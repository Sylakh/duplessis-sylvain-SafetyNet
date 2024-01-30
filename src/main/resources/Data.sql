/* Setting up SAFETYNET DB */
create database safetynet;
use safetynet;


/* TABLE firestation */
create table firestation(
    
    address varchar(255) UNIQUE NOT NULL PRIMARY KEY,
    station varchar(255) NOT NULL
);

insert into firestation(address, station) values("1509 Culver St","3");
insert into firestation(address, station) values("29 15th St","2");
insert into firestation(address, station) values("834 Binoc Ave","3");
insert into firestation(address, station) values("644 Gershwin Cir","1");
insert into firestation(address, station) values("748 Townings Dr","3");
insert into firestation(address, station) values("489 Manchester St","4");
insert into firestation(address, station) values("892 Downing Ct","2");
insert into firestation(address, station) values("908 73rd St","1");
insert into firestation(address, station) values("112 Steppes Pl","4");
insert into firestation(address, station) values("947 E. Rose Dr","1");
insert into firestation(address, station) values("951 LoneTree Rd","2");
commit;

/* TABLE person */
create table person(
    id int PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    zip VARCHAR(10),
    phone VARCHAR(20),
    email VARCHAR(100),
    firestation_address VARCHAR(255),
    medical_record_id INT,
    FOREIGN KEY (firestation_address) REFERENCES firestation(address)
);

insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("John", "Boyd", "John,Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com", "1509 Culver St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Jacob", "Boyd","Jacob,Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com", "1509 Culver St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Tenley", "Boyd","Tenley,Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com", "1509 Culver St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Roger", "Boyd","Roger,Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com", "1509 Culver St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Felicia", "Boyd","Felicia,Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544", "jaboyd@email.com", "1509 Culver St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Jonanathan", "Marrack","Jonanathan,Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com", "29 15th St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Tessa", "Carman","Tessa,Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512", "tenz@email.com", "834 Binoc Ave");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Peter", "Duncan","Peter,Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com", "644 Gershwin Cir");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Foster", "Shepard","Foster,Shepard", "748 Townings Dr", "Culver", "97451", "841-874-6544", "jaboyd@email.com", "748 Townings Dr");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Tony", "Cooper","Tony,Cooper", "489 Manchester St", "Culver", "97451", "841-874-6874", "tcoop@ymail.com", "489 Manchester St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Lily", "Cooper","Lily,Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845", "lily@email.com", "489 Manchester St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Sophia", "Zemicks","Sophia,Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com", "892 Downing Ct");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Warren", "Zemicks","Warren,Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "ward@email.com", "892 Downing Ct");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Zach", "Zemicks","Zach,Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "zarc@email.com", "892 Downing Ct");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address)values("Reginold", "Walker","Reginold,Walker", "908 73rd St", "Culver", "97451", "841-874-8547", "reg@email.com", "908 73rd St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Jamie", "Peters","Jamie,Peters", "908 73rd St", "Culver", "97451", "841-874-7462", "jpeter@email.com", "908 73rd St");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address)values("Ron", "Peters","Ron,Peters", "112 Steppes Pl", "Culver", "97451", "841-874-8888", "jpeter@email.com", "112 Steppes Pl");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Allison", "Boyd","Allison,Boyd", "112 Steppes Pl", "Culver", "97451", "841-874-9888", "aly@imail.com", "112 Steppes Pl");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Brian", "Stelzer","Brian,Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com", "947 E. Rose Dr");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Shawna", "Stelzer","Shawna,Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "ssanw@email.com", "947 E. Rose Dr");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Kendrick", "Stelzer","Kendrick,Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com", "947 E. Rose Dr");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Clive", "Ferguson","Clive,Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com", "748 Townings Dr");
insert into person(firstname, lastname, name, address, city, zip, phone, email,firestation_address) values("Eric", "Cadigan","Eric,Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com", "951 LoneTree Rd");
commit;



/* TABLES medicalrecord */

CREATE TABLE medicalrecord (
    medical_record_id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    birthdate VARCHAR(50),
    person_id INT,
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE Medication (
    medication_id INT AUTO_INCREMENT PRIMARY KEY,
    medical_record_id INT,
    medication_name VARCHAR(255),
    FOREIGN KEY (medical_record_id) REFERENCES medicalrecord(medical_record_id)
);

CREATE TABLE Allergy (
    allergy_id INT AUTO_INCREMENT PRIMARY KEY,
    medical_record_id INT,
    allergy_name VARCHAR(255),
    FOREIGN KEY (medical_record_id) REFERENCES medicalrecord(medical_record_id)
);
commit;

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (1, "John", "Boyd","John,Boyd", "03/06/1984", 1);

SET @john_patient_id = 1;

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@john_patient_id, "aznol:350mg"),
    (@john_patient_id, "hydrapermazol:100mg");

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@john_patient_id, "nillacilan");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (2, "Jacob", "Boyd","Jacob,Boyd", "03/06/1989",2);

SET @jacob_patient_id = 2;

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@jacob_patient_id, "pharmacol:5000mg"),
    (@jacob_patient_id, "terazine:10mg"),
    (@jacob_patient_id, "noznazol:250mg");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (3, "Tenley", "Boyd","Tenley,Boyd", "02/18/2012", 3);

SET @tenley_patient_id = 3;

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@tenley_patient_id, "peanut");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (4, "Roger", "Boyd","Roger,Boyd", "09/06/2017", 4);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (5, "Felicia", "Boyd","Felicia,Boyd", "01/08/1989", 5);

SET @felicia_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@felicia_patient_id, "tetracyclaz:650mg");

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@felicia_patient_id, "xilliathal");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (6, "Jonanathan", "Marrack","Jonanathan,Marrack", "01/03/1989", 6);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (7, "Tessa", "Carman","Tessa,Carman", "02/18/2012", 7);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (8, "Peter", "Duncan","Peter,Duncan", "09/06/2000", 8);

SET @peter_patient_id = LAST_INSERT_ID();

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@peter_patient_id, "shellfish");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (9, "Foster", "Shepard","Foster,Shepard", "01/08/1980", 9);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (10, "Tony", "Cooper","Tony,Cooper", "03/06/1994", 10);

SET @tony_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@tony_patient_id, "hydrapermazol:300mg"),
    (@tony_patient_id, "dodoxadin:0mg");

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@tony_patient_id, "shellfish");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (11, "Lily", "Cooper","Lily,Cooper", "03/06/1994", 11);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (12, "Sophia", "Zemicks","Sophia,Zemicks", "03/06/1988", 12);

SET @sophia_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@sophia_patient_id, "aznol:600mg"),
    (@sophia_patient_id, "hydrapermazol:900mg"),
    (@sophia_patient_id, "pharmacol:5000mg"),
    (@sophia_patient_id, "terazine:500mg");

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@sophia_patient_id, "peanut"),
       (@sophia_patient_id, "shellfish"),
       (@sophia_patient_id, "aznol");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (13, "Warren", "Zemicks","Warren,Zemicks", "03/06/1985", 13);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (14, "Zach", "Zemicks","Zach,Zemicks", "03/06/2017", 14);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (15, "Reginold", "Walker", "Reginold,Walker", "08/30/1979", 15);

SET @reginold_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@reginold_patient_id, "thradox:700mg");

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@reginold_patient_id, "illisoxian");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (16, "Jamie", "Peters","Jamie,Peters", "03/06/1982", 16);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (17, "Ron", "Peters","Ron,Peters", "04/06/1965", 17);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (18, "Allison", "Boyd","Allison,Boyd", "03/15/1965", 18);

SET @allison_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@allison_patient_id, "aznol:200mg");

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@allison_patient_id, "nillacilan");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (19, "Brian", "Stelzer","Brian,Stelzer", "12/06/1975", 19);

SET @brian_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@brian_patient_id, "ibupurin:200mg"),
    (@brian_patient_id, "hydrapermazol:400mg");

INSERT INTO Allergy (medical_record_id, allergy_name)
VALUES (@brian_patient_id, "nillacilan");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (20, "Shawna", "Stelzer", "Shawna,Stelzer", "07/08/1980", 20);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (21, "Kendrick", "Stelzer","Kendrick,Stelzer", "03/06/2014", 21);

SET @kendrick_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@kendrick_patient_id, "noxidian:100mg"),
    (@kendrick_patient_id, "pharmacol:2500mg");

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (22, "Clive", "Ferguson","Clive,Ferguson", "03/06/1994", 22);

-- debut jeu de données
INSERT INTO medicalrecord(medical_record_id, firstname, lastname, name, birthdate, person_id)
VALUES (23, "Eric", "Cadigan","Eric,Cadigan", "08/06/1945", 23);

SET @eric_patient_id = LAST_INSERT_ID();

INSERT INTO Medication (medical_record_id, medication_name)
VALUES
    (@eric_patient_id, "tradoxidine:400mg");


commit;