package com.gmail.damianmajcherq.tspd.employs;

import com.gmail.damianmajcherq.tspd.ITSPDModule;
import com.gmail.damianmajcherq.tspd.MainSystem;
import com.gmail.damianmajcherq.tspd.connection.SqLiteManagement;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EmploysModule implements ITSPDModule {

    private SqLiteManagement sql;
    private MainSystem system;



    @Override
    public void preStart(MainSystem main) {
        this.sql = main.getSqlManagement();
        this.system = main;
    }

    @Override
    public void onStart(MainSystem main) {

        try (Connection con = sql.getConnection() ; Statement st = con.createStatement()) {
            String preStatement;
            preStatement = "CREATE TABLE IF NOT EXISTS employs (" +
                    "id INTEGER UNIQUE ,"+
                    "fname CHAR(40)," +
                    "sname CHAR(40)," +
                    "birth CHAR(10)," +
                    "employed  CHAR(10)" +
                    ");";
            st.execute(preStatement);
        }
        catch (SQLException e) {
            //TODO disable :!:
        }
    }

    private String EmployedViewAccess = "employs.view";

    @Override
    public void initFrames(MainSystem main) {
        if (system.haveAccess(EmployedViewAccess)) {
            Container container = new Container();
            main.registerTabPane("eploys",container,null);
            JTable table = new JTable();

            JScrollPane scroll = new JScrollPane(table);


        }





    }
}
