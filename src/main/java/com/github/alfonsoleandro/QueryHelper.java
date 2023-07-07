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
        return "WITH RECURSIVE race_maxp AS (SELECT race_id, MAX(points) AS nax_points FROM constructor_results GROUP BY race_id) " +
                "SELECT c.name, COUNT(*) as wins " +
                "FROM race_maxp rmp " +
                "         JOIN constructor_results cr ON cr.race_id = rmp.race_id " +
                "         JOIN constructors c ON cr.constructor_id = c.constructor_id " +
                "WHERE cr.points = rmp.nax_points " +
                "  AND cr.constructor_id = (SELECT MIN(constructor_id) " +
                "                           FROM constructor_results " +
                "                           WHERE race_id = rmp.race_id " +
                "                             AND points = rmp.nax_points) " +
                "GROUP BY c.name " +
                "ORDER BY wins DESC " +
                "LIMIT 1;";
    }
}
