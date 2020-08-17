-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS person;

CREATE TABLE person
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(250) NOT NULL,
    lastname  VARCHAR(250) NOT NULL,
    email     VARCHAR(250) NOT NULL,
    phonenr   VARCHAR(250) DEFAULT NULL,
    country   VARCHAR(250) DEFAULT NULL,
    address   VARCHAR(250) DEFAULT NULL
);

INSERT INTO person (firstname, lastname, email, phonenr, country, address)
VALUES ('Karl', 'Wii', 'Karl@gmail.com', '234-3463527', 'Sweden', 'Arsta skolgränd 12B 117 43 Stockholm'),
       ('James', 'KI', 'James@gmail.com', '24-2353485', 'USA', '9462 Brenda Ave Affton, MO 63123'),
       ('Sam', 'Django', 'Sam@gmail.com', '34-85623524', 'Finland', 'Keskusta 40100 Jyväskylä');
