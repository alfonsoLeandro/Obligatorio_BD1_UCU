ALTER TABLE races
    ADD CONSTRAINT fk_races_circuits
        FOREIGN KEY (circuit_id) REFERENCES circuits (circuit_id);

ALTER TABLE pit_stops
    ADD CONSTRAINT fk_pits_race
        FOREIGN KEY (race_id) REFERENCES races (race_id);
ALTER TABLE pit_stops
    ADD CONSTRAINT fk_pits_driver
        FOREIGN KEY (driver_id) REFERENCES drivers (driver_id);

ALTER TABLE driver_standings
    ADD CONSTRAINT fk_standings_race
        FOREIGN KEY (race_id) REFERENCES races (race_id);
ALTER TABLE driver_standings
    ADD CONSTRAINT fk_standings_driver
        FOREIGN KEY (driver_id) REFERENCES drivers (driver_id);

ALTER TABLE lap_times
    ADD CONSTRAINT fk_laps_race
        FOREIGN KEY (race_id) REFERENCES races (race_id);
ALTER TABLE lap_times
    ADD CONSTRAINT fk_laps_driver
        FOREIGN KEY (driver_id) REFERENCES drivers (driver_id);

ALTER TABLE qualifyings
    ADD CONSTRAINT fk_qualifyings_race
        FOREIGN KEY (race_id) REFERENCES races (race_id);
ALTER TABLE qualifyings
    ADD CONSTRAINT fk_qualifyings_driver
        FOREIGN KEY (driver_id) REFERENCES drivers (driver_id);
ALTER TABLE qualifyings
    ADD CONSTRAINT fk_qualifyings_constructor
        FOREIGN KEY (constructor_id) REFERENCES constructors (constuctor_id);

ALTER TABLE results
    ADD CONSTRAINT fk_results_race
        FOREIGN KEY (race_id) REFERENCES races (race_id);
ALTER TABLE results
    ADD CONSTRAINT fk_results_driver
        FOREIGN KEY (driver_id) REFERENCES drivers (driver_id);
ALTER TABLE results
    ADD CONSTRAINT fk_results_constructor
        FOREIGN KEY (constructor_id) REFERENCES constructors (constuctor_id);
ALTER TABLE results
    ADD CONSTRAINT fk_results_status
        FOREIGN KEY (status_id) REFERENCES status (status_id);

ALTER TABLE sprint_results
    ADD CONSTRAINT fk_sprint_results_race
        FOREIGN KEY (race_id) REFERENCES races (race_id);
ALTER TABLE sprint_results
    ADD CONSTRAINT fk_sprint_results_driver
        FOREIGN KEY (driver_id) REFERENCES drivers (driver_id);
ALTER TABLE sprint_results
    ADD CONSTRAINT fk_sprint_results_constructor
        FOREIGN KEY (constructor_id) REFERENCES constructors (constuctor_id);
ALTER TABLE sprint_results
    ADD CONSTRAINT fk_sprint_results_status
        FOREIGN KEY (status_id) REFERENCES status (status_id);

ALTER TABLE constructor_standings
    ADD CONSTRAINT fk_constructor_standings_race
        FOREIGN KEY (race_id) REFERENCES races (race_id);
ALTER TABLE constructor_standings
    ADD CONSTRAINT fk_constructor_standings_constructor
        FOREIGN KEY (constructor_id) REFERENCES constructors (constuctor_id);

ALTER TABLE constructor_results
    ADD CONSTRAINT fk_constructor_results_race
        FOREIGN KEY (race_id) REFERENCES races (race_id);
ALTER TABLE constructor_results
    ADD CONSTRAINT fk_constructor_results_constructor
        FOREIGN KEY (constructor_id) REFERENCES constructors (constuctor_id);
