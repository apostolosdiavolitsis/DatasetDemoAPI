CREATE USER 'apostolos'@'localhost' IDENTIFIED BY '8lfvl94fubBu';
GRANT ALL PRIVILEGES ON *.* TO 'apostolos'@'localhost' WITH GRANT OPTION;


CREATE DATABASE datasetdemo;

USE datasetdemo;

CREATE TABLE Payload (
    id INT NOT NULL,
    createDate VARCHAR(255),
    blobData BLOB,
    PRIMARY KEY (id)
);
CREATE TABLE Dataset (
    id INT NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255),
    payloadId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (payloadId) REFERENCES Payload(id)
);

