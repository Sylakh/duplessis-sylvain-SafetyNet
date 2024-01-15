/* Setting up SAFETYNET DB */
create database safetynet;
use safetynet;


/* TABLE firestation */
create table firestation(
    id int PRIMARY KEY AUTO_INCREMENT,
    address varchar(255) NOT NULL,
    station varchar(255) NOT NULL
);

insert into firestation(address, station) values("1509 Culver St","3");
insert into firestation(address, station) values("29 15th St","2");
insert into firestation(address, station) values("834 Binoc Ave","3");
insert into firestation(address, station) values("644 Gershwin Cir","1");
insert into firestation(address, station) values("748 Townings Dr","3");
insert into firestation(address, station) values("112 Steppes Pl","3");
insert into firestation(address, station) values("489 Manchester St","4");
insert into firestation(address, station) values("892 Downing Ct","2");
insert into firestation(address, station) values("908 73rd St","1");
insert into firestation(address, station) values("112 Steppes Pl","4");
insert into firestation(address, station) values("947 E. Rose Dr","1");
insert into firestation(address, station) values("748 Townings Dr","3");
insert into firestation(address, station) values("951 LoneTree Rd","2");
commit;

/* TABLE person */
create table person(
    id int PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    zip VARCHAR(10),
    phone VARCHAR(20),
    email VARCHAR(100)
);

insert into person(firstname, lastname, address, city, zip, phone, email) values("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544", "jaboyd@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512", "tenz@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Foster", "Shepard", "748 Townings Dr", "Culver", "97451", "841-874-6544", "jaboyd@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Tony", "Cooper", "112 Steppes Pl", "Culver", "97451", "841-874-6874", "tcoop@ymail.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845", "lily@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "ward@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Zach", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "zarc@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Reginold", "Walker", "908 73rd St", "Culver", "97451", "841-874-8547", "reg@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Jamie", "Peters", "908 73rd St", "Culver", "97451", "841-874-7462", "jpeter@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Ron", "Peters", "112 Steppes Pl", "Culver", "97451", "841-874-8888", "jpeter@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Allison", "Boyd", "112 Steppes Pl", "Culver", "97451", "841-874-9888", "aly@imail.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Shawna", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "ssanw@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com");
insert into person(firstname, lastname, address, city, zip, phone, email) values("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com");
commit;

/* TABLE medialrecord */

create table medicalrecord(
    id int PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    birthdate VARCHAR(100),
    medications JSON,
    allergies JSON
);

insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("John", "Boyd", "1984-03-06", '["aznol:350mg", "hydrapermazol:100mg"]', '["nillacilan"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Jacob", "Boyd", "1989-03-06", '["pharmacol:5000m", "terazine:10mg", "noznazol:250mg"]', null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Tenley", "Boyd", "2012-02-18", null, '["peanut"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Roger", "Boyd", "2017-09-06", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Felicia", "Boyd","1986-01-08", '["tetracyclaz:650mg"]', '["xilliathal"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Jonanathan", "Marrack", "1989-01-03", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Tessa", "Carman", "2012-02-18", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Peter", "Duncan", "2000-09-06", null, '["shellfish"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Foster", "Shepard", "1980-01-08", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Tony", "Cooper", "1994-03-06", '["hydrapermazol:300mg", "dodoxadin:30mg"]', '["shellfish"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Lily", "Cooper", "1994-03-06", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Sophia", "Zemicks", "1988-03-06", '["aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"]', '["peanut", "shellfish", "aznol"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Warren", "Zemicks", "1985-03-06", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Zach", "Zemicks", "2017-03-06", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Reginold", "Walker", "1979-08-30", '["thradox:700mg"]', '["illisoxian"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Jamie", "Peters", "1982-03-06", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Ron", "Peters", "1965-04-06", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Allison", "Boyd", "1965-03-15", '["aznol:200mg"]', '["nillacilan"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Brian", "Stelzer", "1975-12-06", '["ibupurin:200mg", "hydrapermazol:400mg"]', '["nillacilan"]');
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Shawna", "Stelzer", "1980-07-08", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Kendrik", "Stelzer", "2014-03-06", '["noxidian:100mg", "pharmacol:2500mg"]', null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Clive", "Ferguson", "1994-03-06", null, null);
insert into medicalrecord(firstname, lastname, birthdate, medications, allergies) values("Eric", "Cadigan", "1945-08-06", '["tradoxidine:400mg"]', null);
commit;

/* TABLES medialrecord */

CREATE TABLE Patient (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    birthdate VARCHAR(50)
);

CREATE TABLE Medication (
    medication_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    medication_name VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id)
);

CREATE TABLE Allergie (
    allergy_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    allergy_name VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id)
);
commit;

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("John", "Boyd", "03/06/1984");

SET @john_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@john_patient_id, "aznol:350mg"),
    (@john_patient_id, "hydrapermazol:100mg");

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@john_patient_id, "nillacilan");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Jacob", "Boyd", "03/06/1989");

SET @jacob_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@jacob_patient_id, "pharmacol:5000mg"),
    (@jacob_patient_id, "terazine:10mg"),
    (@jacob_patient_id, "noznazol:250mg");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Tenley", "Boyd", "02/18/2012");

SET @tenley_patient_id = LAST_INSERT_ID();

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@tenley_patient_id, "peanut");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Roger", "Boyd", "09/06/2017");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Felicia", "Boyd", "01/08/198-");

SET @felicia_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@felicia_patient_id, "tetracyclaz:650mg");

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@felicia_patient_id, "xilliathal");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Jonanathan", "Marrack", "01/03/1989");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Tessa", "Carman", "02/18/2012");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Peter", "Duncan", "09/06/2000");

SET @peter_patient_id = LAST_INSERT_ID();

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@peter_patient_id, "shellfish");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Foster", "Shepard", "01/08/1980");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Tony", "Cooper", "03/06/1994");

SET @tony_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@tony_patient_id, "hydrapermazol:300mg"),
    (@tony_patient_id, "dodoxadin:0mg");

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@tony_patient_id, "shellfish");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Lily", "Cooper", "03/06/1994");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Sophia", "Zemicks", "03/06/1988");

SET @sophia_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@sophia_patient_id, "aznol:600mg"),
    (@sophia_patient_id, "hydrapermazol:900mg"),
    (@sophia_patient_id, "pharmacol:5000mg"),
    (@sophia_patient_id, "terazine:500mg");

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@sophia_patient_id, "peanut"),
       (@sophia_patient_id, "shellfish"),
       (@sophia_patient_id, "aznol");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Warren", "Zemicks", "03/06/1985");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Zack", "Zemicks", "03/06/2017");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Reginold", "Walker", "08/30/1979");

SET @reginold_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@reginold_patient_id, "thradox:700mg");

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@reginold_patient_id, "illisoxian");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Jamie", "Peters", "03/06/1982");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Ron", "Peters", "04/06/1965");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Allison", "Boyd", "03/15/1965");

SET @allison_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@allison_patient_id, "aznol:200mg");

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@allison_patient_id, "nillacilan");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Brian", "Stelzer", "12/06/1975");

SET @brian_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@brian_patient_id, "ibupurin:200mg"),
    (@brian_patient_id, "hydrapermazol:400mg");

INSERT INTO Allergie (patient_id, allergy_name)
VALUES (@brian_patient_id, "nillacilan");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Shawna", "Stelzer", "07/08/1980");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Kendrick", "Stelzer", "03/06/2014");

SET @kendrick_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@kendrick_patient_id, "noxidian:100mg"),
    (@kendrick_patient_id, "pharmacol:2500mg");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Clive", "Ferguson", "03/06/1994");

-- debut jeu de données
INSERT INTO Patient (first_name, last_name, birthdate)
VALUES ("Eric", "Cadigan", "08/06/1945");

SET @eric_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (patient_id, medication_name)
VALUES
    (@eric_patient_id, "tradoxidine:400mg");


commit;