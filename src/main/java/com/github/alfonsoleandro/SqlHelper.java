package com.github.alfonsoleandro;

import java.util.List;

/**
 * Java naming conventions are not followed here, in order to facilitate reflection method invocation.
 *
 * @author alfonsoLeandro
 */
public class SqlHelper {

    private static String compile(StringBuilder query, List<String> args) {
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            query.append(arg);
            if (i == args.size() - 1) {
                query.append(");");
            } else {
                query.append(", ");
            }
        }
        return query.toString();
    }

    public static String circuits(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO circuits (circuit_id, circuit_ref, name, location, country, lat, lng, alt, url) VALUES (");
        return compile(query, args);
    }

    public static String constructor_results(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO constructor_results (constructor_result_id, race_id, constructor_id, points, status) VALUES (");
        return compile(query, args);
    }

    public static String constructor_standings(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO constructor_standings (constructor_standing_id, race_id, constructor_id, points, position, position_text, wins) VALUES (");
        return compile(query, args);
    }

    public static String constructors(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO constructors (constructor_id, constructor_ref, name, nationality, url) VALUES (");
        return compile(query, args);
    }

    public static String driver_standings(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO driver_standings (driver_standing_id, race_id, driver_id, points, position, position_text, wins) VALUES (");
        return compile(query, args);
    }

    public static String drivers(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO drivers (driver_id, driver_ref, number, code, forename, surname, birth_date, nationality, url) VALUES (");
        return compile(query, args);
    }

    public static String lap_times(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO lap_times (race_id, driver_id, lap, position, time, milliseconds) VALUES (");
        return compile(query, args);
    }

    public static String pit_stops(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO pit_stops (race_id, driver_id, stop, lap, time, duration, milliseconds) VALUES (");
        return compile(query, args);
    }

    public static String qualifyings(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO qualifyings (qualifying_id, race_id, driver_id, constructor_id, number, position, q1, q2, q3) VALUES (");
        return compile(query, args);
    }

    public static String races(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO races (race_id, year, round, circuit_id, name, date, time, alt, fp1_date, fp1_time, fp2_date, fp2_time, fp3_date, fp3_time, quali_date, quali_time, sprint_date, sprint_time) VALUES (");
        return compile(query, args);
    }

    public static String results(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO results (result_id, race_id, driver_id, constructor_id, status_id, number, grid, position, position_text, position_order, points, laps, time, milliseconds, rank_, fastest_lap, fastest_lap_time, fastest_lap_speed) VALUES (");
        return compile(query, args);
    }

    public static String seasons(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO seasons (year, url) VALUES (");
        return compile(query, args);
    }

    public static String sprint_results(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO sprint_results (sprint_result_id, race_id, driver_id, constructor_id, status_id, number, grid, position, position_text, position_order, points, laps, time, milliseconds, rank_, fastest_lap, fastest_lap_time, fastest_lap_speed) VALUES (");
        return compile(query, args);
    }

    public static String status(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO status (status_id, status) VALUES (");
        return compile(query, args);
    }


}
