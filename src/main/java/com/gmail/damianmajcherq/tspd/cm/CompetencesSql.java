package com.gmail.damianmajcherq.tspd.cm;

import com.gmail.damianmajcherq.tspd.connection.SqLiteManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CompetencesSql {

    private SqLiteManagement sql;

    public CompetencesSql(SqLiteManagement sql) {
        this.sql = sql;
    }




    public void init() {

        try {
            Connection con = sql.getConnection();
            Statement st = con.createStatement();
            String statement;





            statement = "CREATE TABLE IF NOT EXISTS requirement (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name char(64)," +
                    "description char(256)," +
                    "UNIQUE (name)" +
                    ");";
            st.execute(statement);


            statement = "CREATE TABLE IF NOT EXISTS req_group (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name char(64) UNIQUE NOT NULL," +
                    "parent INTEGER" +
                    ");";
            st.execute(statement);





        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
