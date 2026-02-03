package org.example.demo2.util;

import java.sql.DriverManager;

public class Database {
    private static final String URL = "jdbc:mysql://mysql:3306/demo?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static java.sql.Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
