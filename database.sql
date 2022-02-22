## DROP DATABASE 
DROP DATABASE IF EXISTS FOMS ;
## CREATE DATABASE
Create Database  IF NOT EXISTS FOMS;
## USE DATABASE
USE FOMS;

## TABLE : Users
CREATE TABLE Users(
    ID       int NOT NULL AUTO_INCREMENT,
    Name     varchar(255),
    Email    varchar(50),
    Pswd     varchar(256),
    Img      varchar(50),
    PRIMARY KEY (ID)
    
);

## TABLE : Customers
CREATE TABLE Customers(
    ID          int NOT NULL AUTO_INCREMENT,
    Name        varchar(60),
    Email       varchar(50),
    PhoneNo     varchar(100),
    Address     varchar(100),
    Img         varchar(50),
    PRIMARY KEY (ID),
    UNIQUE (Email),
    UNIQUE (PhoneNo)
          
);

## TABLE : Items
CREATE TABLE Items(
    ID          int NOT NULL AUTO_INCREMENT,
    ItemName    varchar(255),
    ItemDetail  varchar(255),
    Img         varchar(50),
    PRIMARY KEY (ID)
);


## TABLE : Customers Feedback
CREATE TABLE CustomersFeedback(
    ID          int NOT NULL AUTO_INCREMENT,
    OrderId     int,
    FeedBack    Text,
    OrderBy     int, /* Customer ID */
    PRIMARY KEY (ID),
    FOREIGN KEY (OrderBy) REFERENCES Customers(ID) 
);

## TABLE : Orders
CREATE TABLE Orders (
    ID       int NOT NULL AUTO_INCREMENT,
    Items    int,
    OrderBy  int,   /* Customer ID */ 
    AddedBy  int,   /* Accepted / Rejected by Which User */
    Status   varchar(50),
    PRIMARY KEY (ID),
    FOREIGN KEY (Items) REFERENCES Items(ID),
    FOREIGN KEY (AddedBy) REFERENCES Users(ID),
    FOREIGN KEY (OrderBy) REFERENCES Customers(ID)
);



INSERT INTO `users`(`Name`, `Email`, `Pswd`, `Img`) VALUES ('Admin','admin@gmail.com','1234','img');