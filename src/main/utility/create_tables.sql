-- Creating Table Movies
CREATE TABLE public."Movies"
(
   "ID" bigserial NOT NULL,
   "Name" character varying(64),
   "Description" text,
   "PremierDate" date,
   "DurationInMinutes" integer,
   "BudgetInDollars" integer,
   "Rating" numeric(3,2),
   CONSTRAINT "Movies.ID_PK" PRIMARY KEY ("ID")
)
WITH (
  OIDS = FALSE
)
;

-- Creating Table RequestsLog
CREATE TABLE public."RequestsLog"
(
   "ID" bigserial NOT NULL,
   "IP" character varying(16),
   "Date" date,
   "RequestType" character varying(16),
   "Result" character varying(16),
   "AdditionalInfo" text,
   CONSTRAINT "RequestLog.ID_PK" PRIMARY KEY ("ID")
)
WITH (
  OIDS = FALSE
)
;


-- Creating table Ratings
CREATE TABLE public."Ratings"
(
   "ID" bigserial NOT NULL,
   "IP" character varying(16),
   "BrowserFingerprint" text,
   "Mark" smallint,
   "MovieId" bigint,
   CONSTRAINT "IP_UNIQUE" UNIQUE ("IP"),
   CONSTRAINT "BrowserFingerprint_UNIQUE" UNIQUE ("BrowserFingerprint"),
   CONSTRAINT "MovieId_FK" FOREIGN KEY ("MovieId") REFERENCES public."Movies" ("ID") ON UPDATE CASCADE ON DELETE SET NULL,
   CONSTRAINT "Ratings.ID_PK" PRIMARY KEY ("ID")
)
WITH (
  OIDS = FALSE
)
;
COMMENT ON COLUMN public."Ratings"."Mark" IS '1-5';