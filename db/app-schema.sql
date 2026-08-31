-- Esquema propio de la aplicación PortalTrip (reservas de viajes interdimensionales).
-- Se ejecuta después del seed del catálogo (02- en docker-entrypoint-initdb.d).

CREATE TABLE reservations (
  id uuid PRIMARY KEY,
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

CREATE TABLE reservation_companions (
  reservation_id uuid NOT NULL REFERENCES reservations (id) ON DELETE CASCADE,
  character_id integer NOT NULL REFERENCES characters (id),
  PRIMARY KEY (reservation_id, character_id)
);
