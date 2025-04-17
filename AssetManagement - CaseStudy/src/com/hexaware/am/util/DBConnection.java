package com.hexaware.am.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBConnection {
    private static final String PROPERTIES_FILE = "db.properties";
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IOException("Unable to find " + PROPERTIES_FILE);
            }
            Properties prop = new Properties();
            prop.load(input);

            dbUrl = prop.getProperty("db.url");
            dbUsername = prop.getProperty("db.username");
            dbPassword = prop.getProperty("db.password");

        } catch (IOException ex) {
            System.err.println("Error loading database configuration: " + ex.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Failed to establish connection to the database.");
            }
            conn.setAutoCommit(true);
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw e;
        }
    }
}
