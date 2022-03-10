package com.gmail.damianmajcherq.tspd;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Statement;

public class MainModule implements ITSPDModule {


    private SqLiteManagement sql;

    public MainModule(SqLiteManagement sql) {
        this.sql = sql;
    }

    @Override
    public void preStart(MainSystem main) {
        try (Statement st = sql.getConnection().createStatement()) {

            String statement = "CREATE TABLE IF NOT EXISTS employed (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "sys_id int UNIQUE NOT NULL," +
                    "name char(32) NOT NULL," +
                    "s_name char(32) NOT NULL," +
                    "job_start DATE NOT NULL" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS e_groups (" +
                    "gid BIGINT NOT NULL UNIQUE," +
                    "name char(32) NOT NULL," +
                    "deep int(1) NOT NULL" +
                    ");";
            st.execute(statement);



        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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
