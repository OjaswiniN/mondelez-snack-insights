CREATE OR REPLACE FUNCTION tags(json jsonb)
    RETURNS varchar[]
    AS $CODE$
BEGIN
    RETURN ARRAY (
        SELECT
            json_array_elements_text((json->'tags')::json) WHERE (json->'tags')::TEXT <> 'null') t;
END
$CODE$
LANGUAGE plpgsql
IMMUTABLE;

CREATE OR REPLACE FUNCTION extract_json_date(data jsonb, key text)
RETURNS date
IMMUTABLE
LANGUAGE SQL
AS $$
    SELECT (data ->> key)::date;
$$;

--
-- Name: users_snack;
--
CREATE TABLE IF NOT EXISTS user_snacks_log (
    id                  SERIAL NOT NULL,
    user_id             uuid GENERATED ALWAYS AS ((json ->> 'userId')::uuid) STORED NOT NULL,
    type                character varying(64) GENERATED ALWAYS AS (json ->> 'type') STORED NOT NULL,
    name                character varying(64) GENERATED ALWAYS AS (json ->> 'name') STORED NOT NULL,
    quantity            integer GENERATED ALWAYS AS ((json ->> 'quantity')::integer) STORED NOT NULL,
    tags                VARCHAR (128)[] GENERATED ALWAYS AS (tags(json)) STORED,
    purchase_date       DATE GENERATED ALWAYS AS (extract_json_date(json, 'date')) STORED NOT NULL,
    review              text GENERATED ALWAYS AS (json ->> 'review') STORED,
    rating              integer GENERATED ALWAYS AS ((json ->> 'rating')::integer) STORED NOT NULL,
    image               character varying(64) GENERATED ALWAYS AS (json ->> 'image') STORED NOT NULL,
    created_at          BIGINT GENERATED ALWAYS AS ((json ->> 'createdAt')::bigint) STORED NOT NULL,
    json                JSONB NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);