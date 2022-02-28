package com.gmail.damianmajcherq.tspd.cm;

import com.gmail.damianmajcherq.tspd.SqLiteManagement;

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




            statement = "CREATE TABLE IF NOT EXISTS rq_group_info (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tags char(64) NOT NULL," +
                    "description char(256)" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS requirements (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tags char(128)," +
                    "description char(256)," +
                    "req_group int," +
                    "FOREIGN KEY (req_group) REFERENCES rq_group_info(id) ON DELETE SET NULL" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS rq_groups (" +
                    "id_group int NOT NULL," +
                    "id_req int NOT NULL," +
                    "FOREIGN KEY (id_group) REFERENCES rq_group_info(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (id_req) REFERENCES requirements(id) ON DELETE CASCADE" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS knowledge (" +
                    "id_e int NOT NULL," +
                    "req int NOT NULL," +
                    "level real," +
                    "extra varchar(128)," +
                    "FOREIGN KEY (id_e) REFERENCES employed(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (req) REFERENCES requirements(id) ON DELETE CASCADE" +
                    ");";
            st.execute(statement);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
