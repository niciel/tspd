package com.gmail.damianmajcherq.tspd.connection;

import java.io.File;
import java.io.IOException;
import java.sql.*;


public class SqLiteManagement {

    private File DBFile;
    private Connection lastCreatedConnection;


    public SqLiteManagement(File DBFolder) {
        this.DBFile = new File(DBFolder , "tspd.db");
    }



    public void initSqlDatabase() {
        System.out.println("init");

        try {
            if (this.DBFile.exists() == false || this.DBFile.createNewFile()) {
//                TODO
            }
        }
        catch (IOException e) {
//            TODO
            System.out.println("exxception " + e);
        }
    }


    /**
     *
     * @param string
     * @return
     */
    public static boolean checkStatement(String string){
        if (string == null || string.isEmpty())
            return false;
        return string.matches("[='\"]");
    }


    public Connection getConnection() throws SQLException {
        try {
            if (this.lastCreatedConnection != null && lastCreatedConnection.isClosed() == false)
                return this.lastCreatedConnection;
        }
        catch (SQLException e) {
//            TODO
            this.lastCreatedConnection = null;
            System.out.println("exxception " + e);

        }
        this.lastCreatedConnection = DriverManager.getConnection("jdbc:sqlite:"+DBFile.getAbsolutePath());
        return this.lastCreatedConnection;
    }


}
