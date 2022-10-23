CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE accounts (
     "id" UUID DEFAULT public.uuid_generate_v4() PRIMARY KEY UNIQUE,
     "hex_id" VARCHAR(250) NOT NULL,
     "serial" BIGINT NOT NULL UNIQUE,
     "email" VARCHAR(250) NOT NULL,
     "token" VARCHAR(250),
     "name" VARCHAR(250) NOT NULL,
     "avatar" VARCHAR(250),
     "background" VARCHAR(250),
     "phone_numbers" JSONB,
     "title" VARCHAR(250),
     "address" VARCHAR(250),
     "birthday" DATE,
     "notes" VARCHAR(250),
     "tags" JSONB,
     "state" VARCHAR(250) NOT NULL,
     "company_id" VARCHAR(250) NOT NULL,
     "created_by" VARCHAR(250) NOT NULL,
     "created_at" TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
     "updated_by" VARCHAR(250),
     "updated_at" TIMESTAMP WITHOUT TIME ZONE,
     "deleted_by" VARCHAR(250),
     "deleted_at" TIMESTAMP WITHOUT TIME ZONE
);
