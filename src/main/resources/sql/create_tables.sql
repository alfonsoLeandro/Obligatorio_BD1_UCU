CREATE TABLE IF NOT EXISTS constructors
(
    constructor_id  INT PRIMARY KEY,
    constructor_ref VARCHAR(100),
    name           VARCHAR(100),
    nationality    VARCHAR(100),
    url            VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS status
(
    status_id INT PRIMARY KEY,
    status    VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS circuits
(
    circuit_id  INT PRIMARY KEY,
    circuit_ref VARCHAR(100),
    name        VARCHAR(100),
    location    VARCHAR(100),
    country     VARCHAR(100),
    lat         INT,
    lng         INT,
    alt         INT,
    url         VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS races
(
    race_id     INT PRIMARY KEY,
    year        YEAR,
    round       INT,
    circuit_id  INT NOT NULL,
    name        VARCHAR(100),
    date        DATE,
    time        Time,
    alt         VARCHAR(100),
    fp1_date    DATE,
    fp1_time    TIME,
    fp2_date    DATE,
    fp2_time    TIME,
    fp3_date    DATE,
    fp3_time    TIME,
    quali_date  DATE,
    quali_time  TIME,
    sprint_date DATE,
    sprint_time TIME
);

CREATE TABLE IF NOT EXISTS drivers
(
    driver_id   INT PRIMARY KEY,
    driver_ref  VARCHAR(100),
    number      INT,
    code        VARCHAR(3),
    forename    VARCHAR(100),
    surname     VARCHAR(100),
    birth_date  DATE,
    nationality VARCHAR(50),
    url         VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS pit_stops
(
    race_id      INT NOT NULL,
    driver_id    INT NOT NULL,
    stop         INT,
    lap          INT,
    time         TIME,
    duration     TIME(3),
    milliseconds INT
);

CREATE TABLE IF NOT EXISTS driver_standings
(
    driver_standing_id INT PRIMARY KEY,
    race_id            INT NOT NULL,
    driver_id          INT NOT NULL,
    points             INT,
    position           INT,
    position_text      VARCHAR(50),
    wins               INT
);

CREATE TABLE IF NOT EXISTS lap_times
(
    race_id      INT NOT NULL,
    driver_id    INT NOT NULL,
    lap          INT,
    position     INT,
    time         TIME(3),
    milliseconds INT
);

CREATE TABLE IF NOT EXISTS qualifyings
(
    qualifying_id  INT PRIMARY KEY,
    race_id        INT NOT NULL,
    driver_id      INT NOT NULL,
    constructor_id INT NOT NULL,
    number         INT,
    position       INT,
    q1             TIME(3),
    q2             TIME(3),
    q3             TIME(3)
);

CREATE TABLE IF NOT EXISTS results
(
    result_id         INT PRIMARY KEY,
    race_id           INT NOT NULL,
    driver_id         INT NOT NULL,
    constructor_id    INT NOT NULL,
    status_id         INT NOT NULL,
    number            INT,
    grid              INT,
    position          INT,
    position_text     VARCHAR(50),
    position_order    INT,
    points            INT,
    laps              INT,
    time              TIME(3),
    milliseconds      INT,
    rank_             INT,
    fastest_lap       INT,
    fastest_lap_time  TIME(3),
    fastest_lap_speed NUMERIC(6, 3)
);

CREATE TABLE IF NOT EXISTS sprint_results
(
    sprint_result_id  INT PRIMARY KEY,
    race_id           INT NOT NULL,
    driver_id         INT NOT NULL,
    constructor_id    INT NOT NULL,
    status_id         INT NOT NULL,
    number            INT,
    grid              INT,
    position          INT,
    position_text     VARCHAR(50),
    position_order    INT,
    points            INT,
    laps              INT,
    time              TIME(3),
    milliseconds      INT,
    rank_             INT,
    fastest_lap       INT,
    fastest_lap_time  TIME(3),
    fastest_lap_speed NUMERIC(6, 3)
);

CREATE TABLE IF NOT EXISTS constructor_standings
(
    constructor_standing_id INT PRIMARY KEY ,
    race_id                 INT NOT NULL,
    constructor_id          INT NOT NULL,
    points                  INT,
    position                INT,
    position_text           VARCHAR(20),
    wins                    int
);

CREATE TABLE IF NOT EXISTS constructor_results
(
    constructor_result_id INT PRIMARY KEY ,
    race_id               INT NOT NULL,
    constructor_id        INT NOT NULL,
    points                INT,
    status                VARCHAR(1)
);

CREATE TABLE IF NOT EXISTS seasons
(
    year YEAR,
    url  VARCHAR(100)
);