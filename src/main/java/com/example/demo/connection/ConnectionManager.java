package com.example.demo.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;

    public static Connection createConnection() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("./src/main/resources/application.properties")) {
            properties.load(inputStream);
            String uri = properties.getProperty("db.connection.uri");
            String user = properties.getProperty("db.connection.user");
            String password = properties.getProperty("db.connection.password");
            connection = DriverManager.getConnection(uri, user, password);
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Unable to close database connection. Detailed information is bellow. " + e.getMessage());
        }
    }
}
