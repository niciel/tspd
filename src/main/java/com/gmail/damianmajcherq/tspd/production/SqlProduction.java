package com.gmail.damianmajcherq.tspd.production;

import com.gmail.damianmajcherq.tspd.SqLiteManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlProduction {

    private SqLiteManagement sql;


    public SqlProduction(SqLiteManagement sql) {
        this.sql = sql;
    }

    public void init() {
        try (Connection con = this.sql.getConnection();
             Statement st = con.createStatement()) {
            String statement;

            statement = "CREATE TABLE IF NOT EXISTS e_groups (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name char(64) NOT NULL," +
                    "parent int" +
                    ");";
            st.execute(statement);
            statement = "CREATE TABLE IF NOT EXISTS e_groups_r (" +
                    "id int UNIQUE," +
                    "parent int," +
                    "FOREIGN KEY (id) REFERENCES e_groups(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (parent) REFERENCES e_groups(id) ON DELETE SET NULL" +
                    ");";
            st.execute(statement);


            statement = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name char(64) UNIQUE" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS stage (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "order_id INTEGER UNIQUE," +
                    "name char(64) NOT NULL,"+
                    "FOREIGN KEY (order_id) REFERENCES products(id) ON DELETE CASCADE" +
                    ");";
            st.execute(statement);


        }
        catch (SQLException e){e.printStackTrace();};


    }


}
