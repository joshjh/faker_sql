UPDATE teams SET city = city.id FROM city WHERE teams.city = city.name;
ALTER TABLE teams ALTER COLUMN city type INTEGER USING city::integer;
