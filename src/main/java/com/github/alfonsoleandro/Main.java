package com.github.alfonsoleandro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author alfonsoLeandro, Joelit0
 * @since 1.0.0
 */
public class Main {

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
                "jdbc:mysql://localhost:3306/pruebas",
                "root",
                "bernardo");

        createTables();
        runAlters();

        loadData();
    }

    /**
     * Creates all the necessary tables, read from the "tables.sql" file.
     */
    private void createTables() throws SQLException {
        // Read create  tables.sql
    }

    /**
     * Runs alters on every table, in order to add the needed foreign keys.
     */
    private void runAlters() throws SQLException {
        // Read alters from alters.sql
    }

    /**
     * Loads the data from the csv files to the database.
     */
    private void loadData() throws FileNotFoundException {
        File archivo = new File("src/main/resources/archivos/prueba.csv");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
        bufferedReader.lines().forEach( line -> {
            String[] datos = line.split(",");
        });


//        for (File f : archivos.listFiles()) {

//            if (f.isDirectory()) continue;
//            System.out.println(f.getName());
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
//            //Orden de tablas:
//            // 1- circuits
//            // 2- constructor
//            // 3- driver
//            // 4- race
//            // 5- constructor_results
//            // 6- qualifying
//            // 7- pit_stops
//            // 8- results
//            // 9- status
//            // 10- seasons
//            // 11- lap_times
//            // 12- driver_standings
//            // 13- constructor_standings
//            // 14- sprint results
//        }
    }
}