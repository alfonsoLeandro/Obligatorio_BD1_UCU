package com.github.alfonsoleandro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author alfonsoLeandro, Joelit0
 * @since 1.0.0
 */
public class Main {

    private static final String BD_NAME = "obligatorio";

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Connection connection;

    public Main() throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + BD_NAME,
                "root",
                "bernardo");

        boolean tablesExisted = checkIfTablesExist();

        // Creates all the necessary tables, read from the "tables.sql" file.
        executeFile(new File("src/main/resources/sql/create_tables.sql"));

        if (!tablesExisted) {
            // Alters all the necessary tables, read from the "alters.sql" file.
            executeFile(new File("src/main/resources/sql/alter_tables.sql"));
            loadData();
        }


    }

    /**
     * Checks if any table of interest exists in the database.
     */
    private boolean checkIfTablesExist() throws SQLException {
        boolean tablesExist = false;
        DatabaseMetaData metaData = this.connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, BD_NAME, "%", null);
        List<String> tableNames = Arrays.asList("circuits",
                "constructor_results",
                "constructor_standings",
                "constructors",
                "driver_standings",
                "drivers",
                "lap_times",
                "pit_stops",
                "qualifyings",
                "races",
                "results",
                "seasons",
                "sprint_results",
                "status");

        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            if (tableNames.contains(tableName)) {
                System.out.println(tableName);
                System.out.println("existia la tabla \"" + tableName + "\", los alters (foreign keys) no se ejecutarán.");
                tablesExist = true;
                break;
            }
        }

        resultSet.close();
        return tablesExist;
    }


    /**
     * Executes a list of statements in a given file.
     */
    private void executeFile(File inputFile) throws SQLException, FileNotFoundException {
        System.out.println("Ejecutando archivo \"" + inputFile.getName() + "\"...");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        StringBuilder sb = new StringBuilder();
        bufferedReader.lines().forEach(sb::append);
        for (String statement : sb.toString().split(";")) {
            this.connection.prepareStatement(statement + ";").execute();
        }
    }

    /**
     * Loads the data from the csv files to the database.
     */
    private void loadData() throws FileNotFoundException {
        for (String fileName : new String[]{
                "circuits",
                "constructor_results",
                "constructor_standings",
                "constructors",
                "driver_standings",
                "drivers",
                "lap_times",
                "pit_stops",
                "qualifying",
                "races",
                "results",
                "seasons",
                "sprint_results",
                "status"}) {
            System.out.println("Inserting data from \"" + fileName + ".csv\"...");
            loadFile(fileName);
        }

    }

    /**
     * Loads a file and inserts its data to the database.
     */
    private void loadFile(String fileName) throws FileNotFoundException {
        File archivo = new File("src/main/resources/archivos/"+fileName+".csv");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
        bufferedReader.lines().skip(1).forEach(line -> {
            String insertQuery;
            try {
                Method method = SqlHelper.class.getMethod(fileName, List.class);
                insertQuery = (String) method.invoke(null, Arrays.asList(line.split(",")));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            try {
                this.connection.prepareStatement(insertQuery).execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

}