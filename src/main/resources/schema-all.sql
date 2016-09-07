DROP TABLE IF EXISTS people;

CREATE TABLE people (
  person_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  title     VARCHAR(120),
  firstName VARCHAR(120),
  phone     VARCHAR(120),
  state     VARCHAR(120),
  county    VARCHAR(120)
);


