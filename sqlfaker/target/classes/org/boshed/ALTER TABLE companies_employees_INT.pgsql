ALTER TABLE companies
ALTER COLUMN employees TYPE INTEGER USING CAST(employees AS INTEGER);
