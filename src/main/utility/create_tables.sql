-- Creating Table Movies
CREATE TABLE public."movies"
(
   "id" bigserial NOT NULL,
   "name" character varying(64),
   "description" text,
   "premier_date" date,
   "duration_in_minutes" integer,
   "budget_in_dollars" integer,
   "rating" numeric(3,2),
   CONSTRAINT "movies.id_pk" PRIMARY KEY ("id")
)
WITH (
  OIDS = FALSE
)
;

-- Creating Table RequestsLog
CREATE TABLE public."requests_log"
(
   "id" bigserial NOT NULL,
   "ip" character varying(16),
   "date" date,
   "request_type" character varying(16),
   "result" character varying(16),
   "additional_info" text,
   CONSTRAINT "request_log.id_pk" PRIMARY KEY ("id")
)
WITH (
  OIDS = FALSE
)
;


-- Creating table Ratings
CREATE TABLE public."ratings"
(
   "id" bigserial NOT NULL,
   "ip" character varying(16),
   "browser_fingerprint" text,
   "mark" smallint,
   "movie_id" bigint,
   CONSTRAINT "ip_movieid_unique" UNIQUE ("ip", "movie_id"),
   CONSTRAINT "browser_fingerprint_movieid_unique" UNIQUE ("browser_fingerprint", "movie_id"),
   CONSTRAINT "movie_id_fk" FOREIGN KEY ("movie_id") REFERENCES public."movies" ("id") ON UPDATE CASCADE ON DELETE SET NULL,
   CONSTRAINT "ratings.id_pk" PRIMARY KEY ("id")
)
WITH (
  OIDS = FALSE
)
;
COMMENT ON COLUMN public."ratings"."mark" IS '1-5';