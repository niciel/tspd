package com.gmail.damianmajcherq.tspd;

import javax.swing.*;
import java.sql.*;
import java.util.function.Consumer;

public class SqlMainModule implements ITSPDModule {



    private SqLiteManagement sql;


    @Override
    public void preStart(MainSystem main) {
        this.sql = main.getSqlManagement();
        try (Connection connection = sql.getConnection();
                Statement st = connection.createStatement()) {

            String statement = "CREATE TABLE IF NOT EXISTS employed (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "sys_id int UNIQUE NOT NULL," +
                    "name char(32) NOT NULL," +
                    "s_name char(32) NOT NULL," +
                    "job_start DATE NOT NULL" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS e_groups (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gid BIGINT NOT NULL UNIQUE," +
                    "name char(32) NOT NULL," +
                    "deep int(1) NOT NULL" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS emp_to_g (" +
                    "e_id int," +
                    "node int," +
                    "FOREIGN KEY (e_id) REFERENCES employed(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (node) REFERENCES e_groups(id) ON DELETE CASCADE" +
                    ");";
            st.execute(statement);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static final long STRONG_FULL = 0xffffffff;
    private int XFactor = 4; //bigint 64, 2^XFactor = maxIloscGalezi (16), 64/XFactor = maxGlebokosc (16)


    public ResultSet getParents(long gidChild , int deep , Consumer<ResultSet> action) {
        if (deep == 0) {

        }
        else {
            long mask = STRONG_FULL >> (64-XFactor*deep);
            long branch = mask & gidChild;
            String statement = "SELECT * FROM e_groups WHERE(" +
                    "? & gid = ?" +
                    ");";
            try (Connection connection = sql.getConnection();
                 PreparedStatement ps = connection.prepareStatement(statement)) {
                ps.setLong(1,mask);
                ps.setLong(2,branch);
                return ps.executeQuery();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    @Override
    public void onStart(MainSystem main) {

    }

    @Override
    public void initFrames(MainSystem main) {
        JPanel j = new JPanel();


        main.registerTabPane("workers" , j,null);
    }
}
