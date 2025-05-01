package com.main.cleaningservice;

import java.sql.*;

public class DBAdapter {
    private Connection conn;

    void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql:cleaning_service");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
