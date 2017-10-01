# --- !Ups
CREATE TABLE "movie" (
  "imdb_id"         VARCHAR NOT NULL,
  "screen_id"       VARCHAR NOT NULL,
  "movie_title"           VARCHAR NOT NULL,
  "available_seats" INTEGER NOT NULL,
  "reserved_seats"  INTEGER NOT NULL
);
CREATE UNIQUE INDEX "imdb_screen_uq"
  ON "movie" ("imdb_id", "screen_id");


# --- !Downs
DROP TABLE "movie";