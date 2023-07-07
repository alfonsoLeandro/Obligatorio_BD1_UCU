package com.github.alfonsoleandro;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author alfonsoLeandro, Joelit0
 * @since 1.0.0
 */
public class Main {

    private static final String BD_NAME = "obligatorio";
    private final Connection connection;

    public Main() throws ClassNotFoundException, SQLException, IOException {
        System.out.println("Ejecución iniciada: " + new Date());
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

            // Loads results table data, which requires a special process
            loadResultsData();

            // Loads sprint_results table data, which also requires a special process
            loadSprintResultsData();
        }

        System.out.println("Creación de tablas y cargas de datos finalizadas: " + new Date());
        
        // Runs the queries and saves the results to a "results.txt" file
        runQueries();

        System.out.println("Ejecucion finalizada: " + new Date());
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
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
                System.out.println("Existia la tabla \"" + tableName + "\", los alters (foreign keys) y los inserts no se ejecutarán.");
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
    private void loadData() throws IOException {
        for (String fileName : new String[]{
                "circuits",
                "races",
                "constructors",
                "constructor_results",
                "constructor_standings",
                "drivers",
                "status",
                "driver_standings",
                "lap_times",
                "pit_stops",
                "qualifyings",
                "seasons",
        }) {
            System.out.println("Insertando datos desde \"" + fileName + ".csv\"...");
            loadFile(fileName);
        }

    }

    /**
     * Loads data from results.csv file to results table, taking into account time differences
     */
    private void loadResultsData() throws IOException {
        File archivo = new File("src/main/resources/archivos/results.csv");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
        bufferedReader.lines().skip(1).forEach(line -> {
            String[] data = line.split(",");
            StringBuilder insertQuery;

            System.out.println(data[11]);
            if (data[11].contains("+")) {
                insertQuery = new StringBuilder("INSERT INTO results (result_id, race_id, driver_id, constructor_id, number, grid, position, position_text, position_order, points, laps, time, milliseconds, rank_, fastest_lap, fastest_lap_time, fastest_lap_speed, status_id) SELECT ");
                for (int i = 0; i < 11; i++) {
                    insertQuery.append(data[i]).append(", ");
                }
                String minutes = "0";
                String seconds;
                String[] split = data[11]
                        .replace("+", "")
                        .replace("\"", "")
                        .split(":");
                if(data[11].contains(":")){
                    minutes = split[0];
                    seconds = split[1];
                }else{
                    seconds = split[0];
                }

                insertQuery.append("(t1.time + INTERVAL ")
                        .append(minutes)
                        .append(" MINUTE + INTERVAL ")
                        .append(seconds)
                        .append(" SECOND")
                        .append("), ");

                for (int i = 12; i < data.length; i++) {
                    insertQuery.append(data[i]).append(i == data.length - 1 ? " " : ", ");
                }
                insertQuery.append("FROM (SELECT time FROM results WHERE race_id = ")
                        .append(data[1])
                        .append(" ORDER BY time LIMIT 1) AS t1;");

            } else {
                insertQuery = new StringBuilder(DataInsertionHelper.results(Arrays.asList(data)));
            }
            insertQuery = new StringBuilder(insertQuery.toString().replace("\\N", "null"));

            try {
                this.connection.prepareStatement(insertQuery.toString()).execute();
            } catch (SQLException e) {
                System.out.println("Ocurrio un error en el siguiente insert:");
                System.out.println(insertQuery);
                throw new RuntimeException(e);
            }
        });
        bufferedReader.close();
    }


    /**
     * Loads data from results.csv file to results table, taking into account time differences
     */
    private void loadSprintResultsData() throws IOException {
        File archivo = new File("src/main/resources/archivos/sprint_results.csv");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
        bufferedReader.lines().skip(1).forEach(line -> {
            String[] data = line.split(",");
            StringBuilder insertQuery;

            if (data[11].contains("+")) {
                insertQuery = new StringBuilder("INSERT INTO sprint_results (result_id, race_id, driver_id, constructor_id, number, grid, position, position_text, position_order, points, laps, time, milliseconds, fastest_lap, fastest_lap_time, status_id) SELECT ");
                for (int i = 0; i < 11; i++) {
                    insertQuery.append(data[i]).append(", ");
                }
                String minutes = "0";
                String seconds;
                String[] split = data[11]
                        .replace("+", "")
                        .replace("\"", "")
                        .split(":");
                if(data[11].contains(":")){
                    minutes = split[0];
                    seconds = split[1];
                }else{
                    seconds = split[0];
                }

                insertQuery.append("(t1.time + INTERVAL ")
                        .append(minutes)
                        .append(" MINUTE + INTERVAL ")
                        .append(seconds)
                        .append(" SECOND")
                        .append("), ");

                for (int i = 12; i < data.length; i++) {
                    insertQuery.append(data[i]).append(i == data.length - 1 ? " " : ", ");
                }
                insertQuery.append("FROM (SELECT time FROM sprint_results WHERE race_id = ")
                        .append(data[1])
                        .append(" ORDER BY time LIMIT 1) AS t1;");

            } else {
                insertQuery = new StringBuilder(DataInsertionHelper.sprint_results(Arrays.asList(data)));
            }
            insertQuery = new StringBuilder(insertQuery.toString().replace("\\N", "null"));

            try {
                this.connection.prepareStatement(insertQuery.toString()).execute();
            } catch (SQLException e) {
                System.out.println("Ocurrio un error en el siguiente insert:");
                System.out.println(insertQuery);
                throw new RuntimeException(e);
            }
        });
        bufferedReader.close();
    }

    /**
     * Loads a file and inserts its data to the database.
     */
    private void loadFile(String fileName) throws IOException {
        File archivo = new File("src/main/resources/archivos/" + fileName + ".csv");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
        bufferedReader.lines().skip(1).forEach(line -> {
            String insertQuery;
            try {
                Method method = DataInsertionHelper.class.getMethod(fileName, List.class);
                insertQuery = (String) method.invoke(null, Arrays.asList(line.split(",")));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            String finalStringQuery = insertQuery.replace("\\N", "null");
            try {
                this.connection.prepareStatement(finalStringQuery).execute();
            } catch (SQLException e) {
                System.out.println("Ocurrio un error en el siguiente insert:");
                System.out.println(finalStringQuery);
                throw new RuntimeException(e);
            }
        });
        bufferedReader.close();

    }

    /**
     * Runs queries and saves the results to a "results.txt" file in the resources folder.
     */
    private void runQueries() throws IOException, SQLException {
        File results = new File("src/main/resources/procesado/resultados_queries.txt");
        if(results.exists()){
            results.delete();
        }
        results.createNewFile();

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(results));
        fileWriter.write("Resultados: ");
        fileWriter.newLine();
        fileWriter.newLine();

        // Query #2
        ResultSet resultSet2 = this.connection.createStatement().executeQuery(QueryHelper.mostWinsInConstructor());
        List<String> lines2 = new ArrayList<>();
        lines2.add("Scuderia con más victorias históricas: ");
        while (resultSet2.next()){
            lines2.add(resultSet2.getString(1) + " - " + resultSet2.getString(2));
        }


        // Query #4
        ResultSet resultSet4 = this.connection.createStatement().executeQuery(QueryHelper.bigRacesBetweenYears());
        List<String> lines4 = new ArrayList<>();
        lines4.add("Grandes premios entre 1996 y 1999: ");
        while (resultSet4.next()){
            lines4.add(resultSet4.getString(1));
        }


        // Query #5
        ResultSet resultSet5 = this.connection.createStatement().executeQuery(QueryHelper.suzuka97Winner());
        List<String> lines5 = new ArrayList<>();
        lines5.add("Ganador de Suzuka 1997: ");
        while (resultSet5.next()){
            lines5.add(resultSet5.getString(1));
        }


        // Query #6
        ResultSet resultSet6 = this.connection.createStatement().executeQuery(QueryHelper.jacquesVilleneuveVictories());
        List<String> lines6 = new ArrayList<>();
        lines6.add("Victorias de Jacques Villeneuve: ");
        while (resultSet6.next()){
            lines6.add(resultSet6.getString(1) + " - " + resultSet6.getInt(2));
        }


        // Query #7
        ResultSet resultSet7 = this.connection.createStatement().executeQuery(QueryHelper.notPolePositionWinner());
        List<String> lines7 = new ArrayList<>();
        lines7.add("Ganador de más carreras saliendo fuera de pole position: ");
        while (resultSet7.next()){
            lines7.add(resultSet7.getString(1) + " - " + resultSet7.getInt(2));
        }


        // Query #8
        ResultSet resultSet8 = this.connection.createStatement().executeQuery(QueryHelper.polePositionWinner());
        List<String> lines8 = new ArrayList<>();
        lines8.add("Ganador de más carreras saliendo de pole position: ");
        while (resultSet8.next()){
            lines8.add(resultSet8.getString(1) + " - " + resultSet8.getInt(2));
        }

        writeResult(fileWriter, 2, lines2);
        writeResult(fileWriter, 4, lines4);
        writeResult(fileWriter, 5, lines5);
        writeResult(fileWriter, 6, lines6);
        writeResult(fileWriter, 7, lines7);
        writeResult(fileWriter, 8, lines8);

        fileWriter.close();
    }

    private void writeResult(BufferedWriter fileWriter, int queryN, Iterable<String> lines) throws IOException, SQLException {
        fileWriter.write("Query N° " + queryN + ":");
        fileWriter.newLine();

        for (String line : lines) {
            fileWriter.write("\t"+line);
            fileWriter.newLine();
        }

        fileWriter.newLine();
    }

}