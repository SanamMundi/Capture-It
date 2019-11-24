CREATE TABLE Photographer(
   id       INT              NOT NULL PRIMARY KEY,
   name     VARCHAR (20)     NOT NULL,
   age      INT              NOT NULL,
   gender  VARCHAR(10)      NOT NULL,
   address  CHAR (25) ,
   price    DECIMAL (18, 2) 
  
);

CREATE TABLE Items(

item_id   INT           NOT NULL PRIMARY KEY,
name      VARCHAR(20)   NOT NULL,
price     DECIMAL(3,2)  NOT NULL

);
CREATE TABLE rentedItems(
   item_id  INT              NOT NULL,   
   id       INT              NOT NULL,
   bill_id  INT              NOT NULL PRIMARY KEY,
   item_price  DECIMAL(6,2)  NULL,
   amount_due   DECIMAL(18,2) NULL,

   FOREIGN KEY (id) REFERENCES
                Photographer(id) ON DELETE CASCADE,
    FOREIGN KEY(item_id) REFERENCES 
                Items(item_id) ON UPDATE CASCADE                        
);

CREATE TABLE reviews(
    pid     INT     NOT NULL,
    rid     INT     NOT NULL PRIMARY KEY,
    review  VARCHAR() NULL,
    FOREIGN KEY(pid) REFERENCES 
                    Photographer(id) ON DELETE CASCADE,


);
CREATE TABLE User(
    uid     INT         NOT NULL PRIMARY KEY,
    name    VARCHAR(255)  NOT NULL,
    age     INT(2)        NOT NULL,
    address VARCHAR(255)  NOT NULL
);

CREATE TABLE Requests(
    uid   INT     NOT NULL,
    pid   INT      NOT NULL,
    location VARCHAR(255) NOT NULL,
    price     DECIMAL(18,2) NOT NULL,
    date  DATE              NOT NULL,
        FOREIGN KEY(uid) REFERENCES 
                        User(uid) ON DELETE CASCADE,
        FOREIGN KEY(pid) REFERENCES 
                        Photographer(id) ON DELETE CASCADE
                        );