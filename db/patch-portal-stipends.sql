-- Apply on an existing PortalTrip database. Fresh volumes already get this
-- table from app-schema.sql.

CREATE TABLE IF NOT EXISTS portal_stipends (
  id uuid PRIMARY KEY,
  user_id uuid NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  amount numeric(12,2) NOT NULL CHECK (amount > 0),
  created_at timestamptz NOT NULL
);

CREATE INDEX IF NOT EXISTS portal_stipends_user_created_idx
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
