package com.mailer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by iga on 3/23/16.
 */
public class BDConnectionManager {
    private final static String DB_URL= "jdbc:postgresql://127.0.0.1/newBD";
    private final static String DB_USER_NAME = "postgres";
    private final static String DB_USER_PASSWORD = "postgres";

    public Connection getConnection()throws SQLException{

        java.sql.Connection dbConnection = DriverManager.getConnection(DB_URL,DB_USER_NAME, DB_USER_PASSWORD);

        return dbConnection;
    }


}
