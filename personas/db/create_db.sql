CREATE DATABASE springboot;

GRANT ALL ON springboot.* TO appsuser@'%' IDENTIFIED BY 'pappsuser';
GRANT ALL ON springboot.* TO appsuser@localhost IDENTIFIED BY 'pappsuser';

USE springboot;

CREATE TABLE personas (
  personid INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name varchar(100),
  address varchar(200),
  phone varchar(15)
);
