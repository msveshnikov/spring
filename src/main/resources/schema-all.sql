DROP TABLE IF EXISTS people;

CREATE TABLE people (
  person_id INTEGER PRIMARY KEY AUTOINCREMENT,
  title     VARCHAR(120),
  firstName VARCHAR(120),
  phone     VARCHAR(120),
  state     VARCHAR(120),
  county    VARCHAR(120)
);

DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id         INTEGER PRIMARY KEY AUTOINCREMENT,
  title      VARCHAR(120),
  first_Name VARCHAR(120),
  phone      VARCHAR(120),
  state      VARCHAR(120),
  county     VARCHAR(120)
);