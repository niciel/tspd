package com.gmail.damianmajcherq.tspd;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Collection;

public class SqLiteManagement {

    private File DBFile;
    private Connection lastCreatedConnection;


    public SqLiteManagement(File DBFolder) {
        this.DBFile = new File(DBFolder , "tspd.db");
    }

    public Collection<REmployed> GetEmployeds() {
        return null;
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

        try (Statement st = getConnection().createStatement()) {
            String statement = "CREATE TABLE IF NOT EXISTS employed (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "sys_id int UNIQUE NOT NULL," +
                    "name char(32) NOT NULL," +
                    "s_name char(32) NOT NULL," +
                    "job_start DATE NOT NULL" +
                    ");";
            st.execute(statement);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     *  should create temporal table with 0 elements, with copied columns from existing table
     * @param existing
     * @param tempName
     * @return
     */
    public boolean createTempTable(String existing, String tempName)  {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("CREATE TEMP TABLE IF NOT EXISTS " + tempName + " AS SELECT ALL * FROM "+ existing + " WHERE (1=2)" );
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     * should create temporal table with 0 elements, with copied columns from existing table
     * @param existing
     * @param tempName
     * @return
     */
    public boolean createTempTableIndexed(String existing, String tempName)  {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("CREATE TEMP TABLE IF NOT EXISTS " + tempName + " AS SELECT ALL * FROM "+ existing + " WHERE (1=2)" );
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
