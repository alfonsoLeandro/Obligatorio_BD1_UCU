package com.github.alfonsoleandro;

/**
 * Contains the queries to be executed on the dataset previously loaded.
 *
 * @author alfonsoLeandro
 */
public class QueryHelper {

    /**
     * Query #2: Get constructor with most wins
     */
    public static String mostWinsInConstructor() {
        return """
                WITH RECURSIVE race_maxp AS (SELECT race_id, MAX(points) AS nax_points FROM constructor_results GROUP BY race_id)
                SELECT c.name, COUNT(*) as wins FROM race_maxp rmp
                    JOIN constructor_results cr ON cr.race_id = rmp.race_id
                    JOIN constructors c ON cr.constructor_id = c.constructor_id
                WHERE cr.points = rmp.nax_points
                    AND cr.constructor_id = (SELECT MIN(constructor_id)
                                                FROM constructor_results
                                                WHERE race_id = rmp.race_id
                                                AND points = rmp.nax_points)
                GROUP BY c.name
                ORDER BY wins DESC
                LIMIT 1;
                """;
    }

    /**
     * Query #4: Get big races between  1996 and 1999
     */
    public static String bigRacesBetweenYears() {
        return """
                SELECT distinct(name) FROM races
                    WHERE year BETWEEN 1996 AND 1999;
                """;
    }

    /**
     * Query #5: Winner of Suzuka 1997
     */
    public static String suzuka97Winner() {
        return """      
        SELECT CONCAT(d.forename, " ", d.surname) FROM results
            JOIN drivers d ON results.driver_id = d.driver_id
            JOIN races ON results.race_id = races.race_id
        WHERE races.name = 'Japanese Grand Prix'
            AND races.year = 1997 AND position = 1;
        """;
    }

    /**
     * Query #6: Jacques Vulleneuve victories
     */
    public static String jacquesVilleneuveVictories() {
        return """
                SELECT CONCAT(drivers.forename, " ", drivers.surname), COUNT(*) FROM results
                    JOIN drivers ON results.driver_id = drivers.driver_id
                WHERE drivers.forename = 'Jacques'
                    AND drivers.surname = 'Villeneuve'
                    AND position = 1;
                """;
    }
}
