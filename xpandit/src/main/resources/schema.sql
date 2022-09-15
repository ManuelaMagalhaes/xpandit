CREATE TABLE movie (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    title VARCHAR(128) NOT NULL,
    date DATE,
    rank INTEGER,
    revenue BIGINT,
    
    PRIMARY KEY (id)
);