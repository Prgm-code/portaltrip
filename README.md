# PortalTrip API (`portaltrip`)

![Java](https://img.shields.io/badge/Java-26-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-Layered%20Services-blue.svg)
[![Coverage](https://img.shields.io/badge/Coverage-100%25-brightgreen.svg)](https://prgm-code.github.io/portaltrip/)
![Status](https://img.shields.io/badge/Build-Passing-success.svg)

**PortalTrip API** is a REST service built with **Java 26** and **Spring Boot 4.1.1**. It stores the Rick and Morty catalog in PostgreSQL and manages interdimensional trip reservations, server-side quotes, companion validation and reservation state changes.

The application follows the same service pattern as NeonPulse. Controllers depend on service interfaces, `ServiceImpl` classes coordinate each use case and access Spring Data JPA repositories directly. Domain records own their invariants and state transition rules.

[Open the visual system report](https://iy01azmjp64c.postplan.dev)

---

## Project structure and architecture

```text
portaltrip/
├── pom.xml                                            # Maven, Spring Boot and JaCoCo
├── compose.yml                                        # PostgreSQL 17 and seed mounts
├── .env.example                                       # Local environment template
├── bruno/                                             # Executable REST contract collection
├── db/
│   ├── seed.sql                                       # Rick and Morty catalog
│   └── app-schema.sql                                 # Reservation tables
└── src/
    ├── main/
    │   ├── java/cl/prgm/portaltrip/
    │   │   ├── PortaltripApplication.java             # Spring Boot entry point
    │   │   ├── ServletInitializer.java                # WAR deployment initializer
    │   │   │
    │   │   ├── domain/                                # Domain rules and models
    │   │   │   ├── model/
    │   │   │   │   ├── Character.java                # Character domain record
    │   │   │   │   ├── Episode.java                  # Episode domain record
    │   │   │   │   ├── Location.java                 # Location and insurance rule
    │   │   │   │   ├── Quote.java                    # Calculated price breakdown
    │   │   │   │   ├── Reservation.java              # Aggregate and state transitions
    │   │   │   │   ├── ReservationDraft.java         # Self-validating creation input
    │   │   │   │   ├── ReservationStatus.java        # Reservation lifecycle states
    │   │   │   │   ├── RiskLevel.java                # LOW, MEDIUM and HIGH
    │   │   │   │   └── TripType.java                 # Trip type and price multiplier
    │   │   │   ├── service/
    │   │   │   │   └── QuoteCalculator.java          # Quote and risk rules
    │   │   │   └── exception/
    │   │   │       ├── DomainValidationException.java
    │   │   │       ├── InvalidReservationStateException.java
    │   │   │       └── ResourceNotFoundException.java
    │   │   │
    │   │   ├── application/service/                  # Service interfaces and use cases
    │   │   │   ├── CharacterService.java
    │   │   │   ├── CharacterServiceImpl.java
    │   │   │   ├── EpisodeService.java
    │   │   │   ├── EpisodeServiceImpl.java
    │   │   │   ├── LocationService.java
    │   │   │   ├── LocationServiceImpl.java
    │   │   │   ├── QuoteQuery.java
    │   │   │   ├── QuoteService.java
    │   │   │   ├── QuoteServiceImpl.java
    │   │   │   ├── ReservationService.java
    │   │   │   └── ReservationServiceImpl.java
    │   │   │
    │   │   └── infrastructure/
    │   │       ├── persistence/                      # JPA entities and repositories
    │   │       │   ├── CharacterEntity.java
    │   │       │   ├── EpisodeEntity.java
    │   │       │   ├── LocationEntity.java
    │   │       │   ├── ReservationEntity.java
    │   │       │   └── repository/
    │   │       │       ├── CharacterJpaRepository.java
    │   │       │       ├── EpisodeJpaRepository.java
    │   │       │       ├── LocationJpaRepository.java
    │   │       │       └── ReservationJpaRepository.java
    │   │       └── web/                              # REST controllers and DTOs
    │   │           ├── config/OpenApiConfig.java
    │   │           ├── controller/
    │   │           │   ├── CharacterController.java
    │   │           │   ├── EpisodeController.java
    │   │           │   ├── HomeController.java
    │   │           │   ├── LocationController.java
    │   │           │   ├── QuoteController.java
    │   │           │   └── ReservationController.java
    │   │           ├── dto/                          # HTTP request and response records
    │   │           └── exception/GlobalExceptionHandler.java
    │   └── resources/
    │       ├── application.yaml                      # Safe defaults and dev profile selection
    │       ├── application-dev.yaml                  # PostgreSQL, JPA and enabled OpenAPI
    │       ├── application-prod.yaml                 # Validated schema and disabled OpenAPI
    │       └── static/index.html                     # Welcome page
    └── test/java/cl/prgm/portaltrip/                 # 129 automated tests
        ├── domain/                                   # Model and business-rule tests
        ├── application/service/                      # Service tests with mocked JPA repositories
        └── infrastructure/                           # Repository, controller and DTO tests
```

### Request and persistence flow

```mermaid
flowchart TD
    HTTP[HTTP request] --> CONTROLLER[REST Controller]
    CONTROLLER --> CONTRACT[Service interface]
    CONTRACT --> SERVICE[ServiceImpl]
    SERVICE --> DOMAIN[Domain records and business rules]
    SERVICE --> REPOSITORY[Spring Data JpaRepository]
    REPOSITORY --> ENTITY[JPA entity mapping]
    ENTITY --> DATABASE[(PostgreSQL 17)]
```

`ServiceImpl` is the point where both sides meet. It uses domain objects to make business decisions and JPA repositories to load or store data. The controllers never access JPA, and the domain does not import Spring or Jakarta Persistence.

---

## Relational database model and table references

The catalog contains 826 characters, 126 locations and 51 episodes. Docker loads it once from `db/seed.sql`. The application then creates reservations against that catalog through the schema in `db/app-schema.sql`.

```mermaid
erDiagram
    LOCATIONS ||--o{ CHARACTERS : "origin"
    LOCATIONS ||--o{ CHARACTERS : "current location"
    LOCATIONS ||--o{ LOCATION_RESIDENTS : "contains"
    CHARACTERS ||--o{ LOCATION_RESIDENTS : "appears in"
    CHARACTERS ||--o{ CHARACTER_EPISODES : "appears in"
    EPISODES ||--o{ CHARACTER_EPISODES : "contains"
    LOCATIONS ||--o{ RESERVATIONS : "destination"
    RESERVATIONS ||--o{ RESERVATION_COMPANIONS : "includes"
    CHARACTERS ||--o{ RESERVATION_COMPANIONS : "selected as"

    LOCATIONS {
        integer id PK
        string name
        string type
        string dimension
    }

    CHARACTERS {
        integer id PK
        string name
        string status
        string species
        string type
        string gender
        integer origin_id FK
        integer location_id FK
        string image
    }

    EPISODES {
        integer id PK
        string name
        string air_date
        string episode
    }

    LOCATION_RESIDENTS {
        integer location_id PK,FK
        integer character_id PK,FK
    }

    CHARACTER_EPISODES {
        integer character_id PK,FK
        integer episode_id PK,FK
    }

    RESERVATIONS {
        uuid id PK
        string number UK
        string status
        string passenger_name
        string email
        integer destination_id FK
        date travel_date
        integer passengers
        string trip_type
        boolean insurance
        decimal total
        string risk
        timestamp created_at
        timestamp started_at
        timestamp completed_at
    }

    RESERVATION_COMPANIONS {
        uuid reservation_id PK,FK
        integer character_id PK,FK
    }
```

### Foreign keys

1. `characters.origin_id` references `locations.id`.
2. `characters.location_id` references `locations.id`.
3. `location_residents` joins locations and their last known residents.
4. `character_episodes` joins characters and episodes.
5. `reservations.destination_id` references `locations.id`.
6. `reservation_companions` joins reservations and selected characters.

---

## Environment variables and Docker setup

Copy the example configuration before starting the application:

```bash
cp .env.example .env
```

| Variable | Description | Local default |
| :--- | :--- | :--- |
| `SERVER_PORT` | Spring Boot HTTP port | `8080` |
| `DB_HOST` | PostgreSQL hostname | `localhost` |
| `DB_PORT` | PostgreSQL port | `5432` |
| `DB_NAME` | PostgreSQL database | `rickandmorty` |
| `DB_USER` | PostgreSQL user | `rick` |
| `DB_PASSWORD` | PostgreSQL password | `morty` |

Start or stop the API and PostgreSQL with Docker Compose:

```bash
# Build Spring Boot, start the API and initialize PostgreSQL
docker compose up -d --build

# Stop the container without deleting its data
docker compose down
```

Compose exposes the API at `http://localhost:8080`. PostgreSQL stays on the internal
Compose network, and Spring Boot connects to it with the hostname `postgres`.

The official PostgreSQL image runs these files only when it creates the data volume:

```text
db/seed.sql        -> /docker-entrypoint-initdb.d/01-seed.sql
db/app-schema.sql  -> /docker-entrypoint-initdb.d/02-app-schema.sql
```

To recreate the database from scratch, run `docker compose down -v && docker compose up -d`. This deletes existing reservations as well as the catalog volume.

---

## How to run the application

```bash
# Start PostgreSQL
docker compose up -d

# Run PortalTrip with the Maven wrapper
./mvnw spring-boot:run
```

The application starts at `http://localhost:8080`.

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`
- Healthcheck: `http://localhost:8080/health`

The default local profile is `dev`; it enables Swagger UI and OpenAPI. Production requires explicit database variables, validates the existing schema and disables both documentation endpoints:

```bash
SPRING_PROFILES_ACTIVE=prod \
DB_HOST=localhost DB_PORT=5432 DB_NAME=rickandmorty \
DB_USER=rick DB_PASSWORD=morty \
./mvnw spring-boot:run
```

---

## Testing and JaCoCo coverage

The suite contains **129 tests**. Maven generates the console and HTML reports during `test`, and enforces 100% instruction and branch coverage during `verify`.

```bash
# Run tests
./mvnw clean test

# Run tests and enforce the coverage thresholds
./mvnw clean verify
```

The HTML report is generated at:

```text
target/site/jacoco/index.html
```

**[Open the JaCoCo report published by GitHub Actions](https://prgm-code.github.io/portaltrip/)**

Current verified coverage:

| Counter | Covered | Result |
| :--- | ---: | :--- |
| Instructions | 2390 / 2390 | 100% |
| Branches | 92 / 92 | 100% |
| Lines | 500 / 500 | 100% |
| Methods | 157 / 157 | 100% |

GitHub Actions executes `./mvnw -B clean verify` on pull requests and pushes to `main`. It retains the Surefire and JaCoCo artifacts for 14 days and publishes the successful `main` report with GitHub Pages.

---

## REST API documentation

Every `/api/v1` endpoint returns the same response envelope:

```json
{
  "status": 200,
  "message": "Quote calculated successfully",
  "data": {},
  "timestamp": "2026-08-30T22:49:00Z"
}
```

### Catalog endpoints

| Method | Endpoint | Description | Status codes |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/locations` | List location summaries | `200 OK` |
| `GET` | `/api/v1/locations/{id}` | Get a location with resident IDs | `200 OK`, `400 Bad Request`, `404 Not Found` |
| `GET` | `/api/v1/characters` | List character summaries | `200 OK` |
| `GET` | `/api/v1/characters/{id}` | Get a character with episode IDs | `200 OK`, `400 Bad Request`, `404 Not Found` |
| `GET` | `/api/v1/episodes` | List episode summaries | `200 OK` |
| `GET` | `/api/v1/episodes/{id}` | Get an episode with character IDs | `200 OK`, `400 Bad Request`, `404 Not Found` |

### Quote endpoint

| Method | Endpoint | Description | Status codes |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/quotes` | Calculate price and risk without persisting data | `200 OK`, `400 Bad Request`, `404 Not Found` |

Example request:

```json
{
  "destinationId": 3,
  "passengers": 2,
  "tripType": "exploration",
  "insurance": false
}
```

Example response data:

```json
{
  "basePrice": 1200,
  "locationSurcharge": 300,
  "passengerSurcharge": 216,
  "tripSurcharge": 360,
  "insuranceCost": 380,
  "total": 2456,
  "risk": "MEDIUM"
}
```

### Reservation endpoints

| Method | Endpoint | Description | Status codes |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/reservations` | Validate, quote and create a confirmed reservation | `201 Created`, `400 Bad Request`, `404 Not Found`, `422 Unprocessable Entity` |
| `GET` | `/api/v1/reservations` | List reservations from newest to oldest | `200 OK` |
| `GET` | `/api/v1/reservations/{id}` | Get reservation detail | `200 OK`, `400 Bad Request`, `404 Not Found` |
| `PATCH` | `/api/v1/reservations/{id}/start` | Move `CONFIRMED` to `IN_PROGRESS` | `200 OK`, `404 Not Found`, `409 Conflict` |
| `PATCH` | `/api/v1/reservations/{id}/complete` | Move `IN_PROGRESS` to `COMPLETED` | `200 OK`, `404 Not Found`, `409 Conflict` |
| `PATCH` | `/api/v1/reservations/{id}/cancel` | Cancel a non-terminal reservation | `200 OK`, `404 Not Found`, `409 Conflict` |

Example creation request:

```json
{
  "passengerName": "Morty Smith",
  "email": "morty@example.com",
  "destinationId": 1,
  "travelDate": "2099-12-20",
  "passengers": 2,
  "companionIds": [1, 2],
  "tripType": "exploration",
  "insurance": true,
  "comments": "Window seat"
}
```

### Error handling

| Scenario | HTTP status | Response message |
| :--- | :--- | :--- |
| Resource not found | `404 Not Found` | Identifies the missing resource and ID |
| Invalid HTTP input | `400 Bad Request` | Lists invalid fields and validation messages |
| Domain rule violation | `422 Unprocessable Entity` | Returns `Validation failed` and the domain errors in `data` |
| Invalid state transition | `409 Conflict` | Identifies the current and requested reservation states |
| Unexpected server error | `500 Internal Server Error` | Returns a generic message and logs the exception server-side |

---

## Business rules

### Quote calculation

| Concept | Rule |
| :--- | :--- |
| Base price | 1200 credits |
| Trip type | `express` x1, `exploration` x1.3, `premium` x1.65 |
| Extra passengers | 18% of the base price per additional passenger |
| Space station | 25% surcharge when the location type contains `station` |
| Insurance | 190 credits per passenger, mandatory for an unknown dimension |
| Risk | `HIGH` with no residents, `MEDIUM` for an unknown dimension or fewer than five residents, `LOW` otherwise |

### Reservation validation

- `ReservationRequestDto` validates the HTTP request with Jakarta Validation.
- `ReservationDraft` checks its field invariants when constructed.
- `Reservation.confirm` checks companion status and destination insurance before creating the aggregate.
- `ReservationServiceImpl` verifies that every requested companion exists, then coordinates domain validation, quote calculation and persistence.

### Reservation lifecycle

```mermaid
stateDiagram-v2
    [*] --> CONFIRMED
    CONFIRMED --> IN_PROGRESS: start
    CONFIRMED --> CANCELLED: cancel
    IN_PROGRESS --> COMPLETED: complete
    IN_PROGRESS --> CANCELLED: cancel
    COMPLETED --> [*]
    CANCELLED --> [*]
```

`COMPLETED` and `CANCELLED` are terminal states. An invalid transition returns `409 Conflict`.

---

## cURL testing guide

```bash
# 1. Healthcheck
curl -i http://localhost:8080/health

# 2. List locations
curl -i http://localhost:8080/api/v1/locations

# 3. Get location detail
curl -i http://localhost:8080/api/v1/locations/3

# 4. Calculate a quote
curl -i -X POST http://localhost:8080/api/v1/quotes \
  -H "Content-Type: application/json" \
  -d '{
    "destinationId": 3,
    "passengers": 2,
    "tripType": "exploration",
    "insurance": false
  }'

# 5. Create a reservation
curl -i -X POST http://localhost:8080/api/v1/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "passengerName": "Morty Smith",
    "email": "morty@example.com",
    "destinationId": 1,
    "travelDate": "2099-12-20",
    "passengers": 2,
    "companionIds": [1, 2],
    "tripType": "exploration",
    "insurance": true,
    "comments": "Window seat"
  }'

# 6. Start a reservation
curl -i -X PATCH http://localhost:8080/api/v1/reservations/{id}/start

# 7. Complete a reservation
curl -i -X PATCH http://localhost:8080/api/v1/reservations/{id}/complete
```

---

## Bruno contract collection

Open the `bruno/` directory in Bruno, select the `local` environment and run the collection in sequence. It creates a reservation, stores its generated UUID in memory, advances it to `COMPLETED` and verifies that a later cancellation returns `409 Conflict`.

The same audit can be run from the CLI while the application is listening on port 8080:

```bash
cd bruno
bru run --env-file environments/local.bru --bail
```

---

## Logging

PortalTrip uses Spring Boot's default SLF4J and Logback configuration. Normal application output goes to the console at `INFO` level.

`GlobalExceptionHandler` logs unexpected exceptions with their stack traces and returns `Internal server error` to the client. Expected `400`, `404`, `409` and `422` responses are not logged as server failures.

Package-specific levels can be configured in `src/main/resources/application.yaml`:

```yaml
logging:
  level:
    root: INFO
    cl.prgm.portaltrip: DEBUG
```

---
