CREATE OR REPLACE FUNCTION is_same(x text, t text) RETURNS integer
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT
RETURN cast(lower(' ' || t || '!') like lower('% ' || x || '%') as integer);