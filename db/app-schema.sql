-- Esquema propio de la aplicación PortalTrip (usuarios y reservas de viajes interdimensionales).
-- Se ejecuta después del seed del catálogo (02- en docker-entrypoint-initdb.d).

CREATE TABLE users (
  id uuid PRIMARY KEY,
  email varchar(320) NOT NULL UNIQUE,
  password_hash varchar(255) NOT NULL,
  full_name varchar(100) NOT NULL,
  role varchar(30) NOT NULL,
  balance numeric(12,2) NOT NULL CHECK (balance >= 0),
  version bigint NOT NULL DEFAULT 0,
  created_at timestamptz NOT NULL,
  updated_at timestamptz NOT NULL
);

CREATE TABLE reservations (
  id uuid PRIMARY KEY,
  user_id uuid NOT NULL REFERENCES users (id),
  idempotency_key uuid NOT NULL,
  number text NOT NULL UNIQUE,
  status text NOT NULL,                 -- CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
  passenger_name text NOT NULL,
  email text NOT NULL,
  destination_id integer NOT NULL REFERENCES locations (id),
  travel_date date NOT NULL,
  passengers integer NOT NULL,
  trip_type text NOT NULL,              -- express, exploration, premium
  insurance boolean NOT NULL,
  comments text NOT NULL DEFAULT '',
  base_price numeric(10,2) NOT NULL,
  location_surcharge numeric(10,2) NOT NULL,
  passenger_surcharge numeric(10,2) NOT NULL,
  trip_surcharge numeric(10,2) NOT NULL,
  insurance_cost numeric(10,2) NOT NULL,
  total numeric(10,2) NOT NULL,
  risk text NOT NULL,                   -- LOW, MEDIUM, HIGH
  created_at timestamptz NOT NULL DEFAULT now(),
  started_at timestamptz,
  completed_at timestamptz
);

CREATE UNIQUE INDEX reservations_user_idempotency_key_uq
  ON reservations (user_id, idempotency_key);

CREATE TABLE reservation_companions (
  reservation_id uuid NOT NULL REFERENCES reservations (id) ON DELETE CASCADE,
  character_id integer NOT NULL REFERENCES characters (id),
  PRIMARY KEY (reservation_id, character_id)
);

CREATE TABLE portal_stipends (
  id uuid PRIMARY KEY,
  user_id uuid NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  amount numeric(12,2) NOT NULL CHECK (amount > 0),
  created_at timestamptz NOT NULL
);

CREATE INDEX portal_stipends_user_created_idx
  ON portal_stipends (user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS portal_activity (
  user_id uuid PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
  cycle_id uuid NOT NULL,
  started_at timestamptz NOT NULL,
  sampled_at timestamptz NOT NULL,
  sequence integer NOT NULL DEFAULT 0,
  active_ms bigint NOT NULL DEFAULT 0,
  distance double precision NOT NULL DEFAULT 0,
  payout numeric(12,2) NOT NULL DEFAULT 0
);
