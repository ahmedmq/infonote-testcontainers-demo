CREATE TABLE `NOTES`
(
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `TITLE` VARCHAR(255) NOT NULL,
    `CONTENT` VARCHAR(2000),
    `AUTHOR` VARCHAR(30),
    `CREATED_AT` DATETIME,
    PRIMARY KEY ( `ID` )
);

INSERT INTO NOTES VALUES(99,'INIT_TITLE','INIT_CONTENT','ADMIN',NOW())
