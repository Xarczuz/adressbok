DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  firstname VARCHAR(250) NOT NULL,
  lastname VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  phonenr VARCHAR(250) DEFAULT NULL
);

INSERT INTO person (firstname, lastname, email, phonenr) VALUES
  ('Karl', 'Wii', 'Karl@gmail.com','234-3463527'),
  ('James', 'KI', 'James@gmail.com','24-2353485'),
  ('Sam', 'Django', 'Sam@gmail.com','34-85623524');
