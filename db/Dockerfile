# PostgreSQL 17 image with the PortalTrip catalog and reservation schema baked in.
# Used by compose.coolify.yml so hosts that rewrite bind mounts (Coolify) still
# initialize the database. The official entrypoint runs these files in order the
# first time the data volume is created.
FROM postgres:17-alpine

COPY db/seed.sql       /docker-entrypoint-initdb.d/01-seed.sql
COPY db/app-schema.sql /docker-entrypoint-initdb.d/02-app-schema.sql
