-- Flyway migration: Add notes to beer_order table
ALTER TABLE beer_order ADD COLUMN notes VARCHAR(255);
