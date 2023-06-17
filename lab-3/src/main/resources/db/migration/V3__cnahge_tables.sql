
CREATE TABLE IF NOT EXISTS USERS (
                                     ID INT NOT NULL AUTO_INCREMENT
    , NAME INT REFERENCES CAR_MARK (ID) on delete cascade
    , PASSWORD VARCHAR(100) NOT NULL
    , PRIMARY KEY (ID)
    );

CREATE TABLE IF NOT EXISTS ROLES (
     ID INT NOT NULL AUTO_INCREMENT
    , NAME ENUM('ROLE_ADMIN', 'ROLE_USER')
    , PRIMARY KEY (ID)
    );

CREATE TABLE USER_ROLES(
                                     ROLE_ID INT(15) NOT NULL,
                                     USER_ID INT(14) NOT NULL,
                                     FOREIGN KEY (ROLE_ID) REFERENCES ROLES(ID),
                                     FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
                                     UNIQUE (ROLE_ID, USER_ID)
);

INSERT INTO ROLES (ID, NAME)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLES (ID, NAME)
VALUES (2, 'ROLE_USER');