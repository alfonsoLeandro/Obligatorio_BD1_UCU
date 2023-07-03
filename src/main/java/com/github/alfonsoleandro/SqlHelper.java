package com.github.alfonsoleandro;

import java.util.List;

/**
 * Java naming conventions are not followed here, in order to facilitate reflection method invocation.
 *
 * @author alfonsoLeandro
 */
public class SqlHelper {

    public static String circuits(List<String> args) {
        StringBuilder query = new StringBuilder("INSERT INTO circuits (circuit_id, circuit_ref, name, location, country, lat, lng, alt, url) VALUES (");
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


}
