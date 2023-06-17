
CREATE TABLE IF NOT EXISTS CAR_MARK (
                          ID LONG NOT NULL AUTO_INCREMENT
    , NAME VARCHAR(60) NOT NULL
    , RELEASE_DATE DATE
    , PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS CAR_MODEL (
                           ID INT NOT NULL AUTO_INCREMENT
    , CAR_MARK_ID INT NOT NULL REFERENCES CAR_MARK (ID) on delete cascade
    , NAME VARCHAR(100) NOT NULL
    , LENGTH INT NOT NULL
    , WIDTH INT NOT NULL
    , BODY_TYPE ENUM('SEDAN',
                               'HATCHBACK',
                               'STATION_WAGON',
                               'COUPE',
                               'WAGON',
                               'ROADSTER')
    , PRIMARY KEY (ID)
);
