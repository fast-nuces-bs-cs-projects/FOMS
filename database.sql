## DROP DATABASE 
DROP DATABASE IF EXISTS FOMS ;
## CREATE DATABASE
Create Database  IF NOT EXISTS FOMS;
## USE DATABASE
USE FOMS;



## TABLE : Customers
CREATE TABLE Customers(
    ID          int NOT NULL AUTO_INCREMENT,
    Name        varchar(60),
    Email       varchar(50),
    PhoneNo     varchar(100),
    Address     varchar(100),
    PRIMARY KEY (ID),
    UNIQUE (Email)
);

## TABLE : Customers Feedback
CREATE TABLE CustomersFeedback(
    ID          int NOT NULL AUTO_INCREMENT,
    OrderNo     int,
    FeedBack    Text,
    PRIMARY KEY (ID)  
);

## TABLE : Orders
CREATE TABLE Orders (
    ID       int NOT NULL AUTO_INCREMENT,
    Items    varchar(255),
    OrderBy  int, 
    PRIMARY KEY (ID)
);


## TABLE : Users
CREATE TABLE Users(
    ID       int NOT NULL AUTO_INCREMENT,
    Name     varchar(255),
    Email    varchar(50),
    Pswd     varchar(256),
    PRIMARY KEY (ID)
    
);

## TABLE : Items
CREATE TABLE Items(
    ID          int NOT NULL AUTO_INCREMENT,
    ItemName    varchar(255),
    ItemDetail  varchar(255),
    PRIMARY KEY (ID)
);