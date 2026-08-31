# PortalTrip API

REST API built with Spring Boot 4 (Java 26) following a hexagonal architecture on PostgreSQL 17. It serves the interdimensional catalog (Rick and Morty characters, locations and episodes) and manages trip reservations for the planner.

Visual system report: https://iy01azmjp64c.postplan.dev

![tests](https://img.shields.io/badge/tests-130%20passing-brightgreen) ![coverage](https://img.shields.io/badge/JaCoCo%20coverage-100%25-brightgreen)

PortalTrip replaces the frontend's direct consumption of the public Rick and Morty API and its `localStorage` persistence with a dedicated backend split into two blocks:

- **Catalog (read-only)**: 826 characters, 126 locations and 51 episodes, imported once from the public API and seeded into PostgreSQL with their relationships.
- **Reservations (read/write)**: reservation creation with validation, server-side quoting, and a state-machine lifecycle.

The entire API is an English contract (`/api/v1/characters`, `passengerName`, `CONFIRMED`...); the only Spanish texts are the validation messages shown to the end user and the Swagger documentation summaries.

## Hexagonal architecture

Strict ports and adapters: the domain knows nothing about frameworks, the application depends on interfaces (ports), and the infrastructure implements them (adapters).

```
┌────────────────────────────── infrastructure ──────────────────────────────┐
│ web/                          persistence/                                 │
│  controllers (REST)            JPA entities + Spring Data repositories      │
│  dtos + jakarta validation     PersistenceAdapters ─┐                       │
│  GlobalExceptionHandler                             │ implement             │
└───────────┬─────────────────────────────────────────┼───────────────────────┘
            │ uses                                     │
┌───────────▼──────────── application ────────────────▼───────────────────────┐
│ port/in  (use cases):  LocationService, CharacterService, EpisodeService,   │
│                        QuoteService, ReservationService                     │
│ port/out (repositories): LocationRepository, CharacterRepository,           │
│                          EpisodeRepository, ReservationRepository           │
│ service (impls): QuoteServiceImpl, ReservationServiceImpl, ...              │
└───────────┬──────────────────────────────────────────────────────────────────┘
            │ uses
┌───────────▼──────────── domain (plain Java, no annotations) ────────────────┐
│ model: Character, Location, Episode, Reservation, Quote, TripType,          │
│        RiskLevel, ReservationStatus (state machine)                         │
│ service: QuoteCalculator (pricing and risk), ReservationValidator           │
│ exception: ResourceNotFoundException (404), DomainValidationException (400),│
│            InvalidReservationStateException (409)                           │
└──────────────────────────────────────────────────────────────────────────────┘
```

Dependencies always point inwards: application services never import Spring Data or JPA entities; they work only with domain objects.

## Data model (PostgreSQL)

Seven tables in the `rickandmorty` database. Many-to-many relationships are resolved through join tables; the public API `url` fields were removed (only `characters.image` remains, pointing to the remote avatar).

| Table | Contents | Relationships |
| --- | --- | --- |
| `locations` | 126 locations (name, type, dimension) | — |
| `characters` | 826 characters (status, species, gender, image) | `origin_id` and `location_id` → `locations` |
| `episodes` | 51 episodes (air date, code S01E01) | — |
| `location_residents` | 804 join rows | location ↔ residents |
| `character_episodes` | 1267 join rows | character ↔ episodes |
| `reservations` | Reservation: passenger, email, travel date, passenger count, trip type, insurance, quote breakdown, risk, status and timestamps | `destination_id` → `locations` |
| `reservation_companions` | Join table | reservation ↔ companion characters |

Startup is reproducible on any machine: Docker Compose mounts two scripts into `/docker-entrypoint-initdb.d/`, which the official Postgres image runs only when the data volume is first initialized:

```
db/seed.sql        → 01-seed.sql        (full catalog: schema + 1003 INSERTs)
db/app-schema.sql  → 02-app-schema.sql  (reservation tables, empty)
```

## Endpoints

| Method | Route | Description | Responses |
| --- | --- | --- | --- |
| GET | `/api/v1/locations` | Location list (summary) | 200 |
| GET | `/api/v1/locations/{id}` | Detail with `residentIds` | 200, 404 |
| GET | `/api/v1/characters` · `/{id}` | Character catalog | 200, 404 |
| GET | `/api/v1/episodes` · `/{id}` | Episode catalog | 200, 404 |
| POST | `/api/v1/quotes` | Quotes a trip without creating anything | 200, 400, 404 |
| POST | `/api/v1/reservations` | Creates a reservation (validates + quotes + persists) | 201, 400 |
| GET | `/api/v1/reservations` · `/{id}` | Reservation list and detail | 200, 404 |
| PATCH | `/api/v1/reservations/{id}/start` | CONFIRMED → IN_PROGRESS (sets `startedAt`) | 200, 404, 409 |
| PATCH | `/api/v1/reservations/{id}/complete` | IN_PROGRESS → COMPLETED (sets `completedAt`) | 200, 404, 409 |
| PATCH | `/api/v1/reservations/{id}/cancel` | → CANCELLED (not allowed from COMPLETED) | 200, 404, 409 |
| GET | `/health` | Healthcheck | 200 |

Interactive documentation at `/swagger-ui.html` (springdoc-openapi).

Every `/api/v1` response uses the same envelope. `status` matches the HTTP status code, `message` describes the result, `data` contains the resource or validation errors, and `timestamp` records when the response was created. Jackson omits `data` when it is `null`.

```json
{
  "status": 200,
  "message": "Quote calculated successfully",
  "data": {},
  "timestamp": "2026-08-30T22:49:00Z"
}
```

## Business rules

### Quoting (`QuoteCalculator`)

| Concept | Rule |
| --- | --- |
| Base price | 1200 credits |
| Trip type | express ×1 · exploration ×1.3 · premium ×1.65 |
| Extra passengers | +18% of base per additional passenger |
| Space station | +25% if the location type contains "station" |
| Insurance | 190 per passenger; **mandatory** (forced) when the dimension is "unknown" |
| Risk | HIGH with no residents · MEDIUM if dimension "unknown" or <5 residents · LOW otherwise |

### Reservation validation (`ReservationValidator`)

- Passenger name of at least 3 characters and a valid email format.
- Existing destination and a future travel date.
- Between 1 and 8 passengers; at most 3 companions, all with "Alive" status.
- Interdimensional insurance required for unknown dimensions.

### Lifecycle (`ReservationStatus`)

```
CONFIRMED ──start──▶ IN_PROGRESS ──complete──▶ COMPLETED
    │                      │
    └────────cancel────────┴──▶ CANCELLED      (COMPLETED is terminal)
```

Every illegal transition responds 409, e.g.: `Reservation 'PT-2026-238413' cannot transition from COMPLETED to CANCELLED`.

## Verified end-to-end flow (smoke test)

Sequence executed against the real seeded PostgreSQL:

```
POST /api/v1/quotes        { destinationId: 3, passengers: 2, tripType: "exploration" }
  → 200  { status: 200, message: "Quote calculated successfully",
           data: { basePrice: 1200, locationSurcharge: 300, passengerSurcharge: 216,
                   tripSurcharge: 360, insuranceCost: 380, total: 2456, risk: "MEDIUM" } }
           (Citadel of Ricks: space station + unknown dimension → forced insurance)

POST /api/v1/reservations  { passengerName: "Morty Smith", companionIds: [1, 2], ... }
  → 201  { status: 201, data: { number: "PT-2026-238413", status: "CONFIRMED", quote: { ... } } }

PATCH /api/v1/reservations/{id}/start     → 200  IN_PROGRESS  (startedAt)
PATCH /api/v1/reservations/{id}/complete  → 200  COMPLETED    (completedAt)
PATCH /api/v1/reservations/{id}/cancel    → 409  (COMPLETED is terminal)
POST with an invalid email                → 400  (list of validation errors)
```

## Running it

```bash
docker compose up -d          # PostgreSQL 17 + automatic seed (first run only)
./mvnw spring-boot:run        # API at http://localhost:8080
./mvnw verify                 # 130 tests + 100% coverage check
```

> The seed runs only when the volume is first initialized. To rebuild the database: `docker compose down -v && docker compose up -d` (wipes all data, including reservations).

The connection is configured through the environment (see `.env.example`): `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, with defaults matching the local compose setup.

## Next step

Wire the frontend (Astro, currently pointing at the public Rick and Morty API and persisting to `localStorage`) to this backend: change the HTTP client's base URL, map the status codes (`CONFIRMED`, `LOW`...) to the Spanish labels used by the UI, and replace the store's local persistence with calls to `/api/v1/reservations`.
