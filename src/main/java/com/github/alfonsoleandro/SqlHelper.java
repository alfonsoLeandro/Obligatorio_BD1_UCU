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
            if(i == args.size() - 1) {
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

    public static String constructors(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO constructors (constructor_id, constructor_ref, name, nationality, url) VALUES (");
        return compile(query, args);
    }

    public static String status(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO status (status_id, status) VALUES (");
        return compile(query, args);
    }


}
