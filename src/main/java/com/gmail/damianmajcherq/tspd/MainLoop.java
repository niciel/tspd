package com.gmail.damianmajcherq.tspd;


import com.gmail.damianmajcherq.tspd.cm.CompetencesSql;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;


public class MainLoop {

    private static File ExecutionFolder;
    private ArrayList<data> data = new ArrayList<>();


    private static MainModule MAIN;


    public static void main(String args[]){
        System.out.println(System.getenv("APPDATA"));
        //MainLoop loop = new MainLoop();
        MAIN = new MainModule();
        ExecutionFolder = new File("").getAbsoluteFile();
        MAIN.init(new SqLiteManagement(ExecutionFolder));

    }

    private MainLoop() {

        System.out.println("execution: " + this.ExecutionFolder);

        SqLiteManagement sql = new SqLiteManagement(this.ExecutionFolder);
        sql.initSqlDatabase();

        CompetencesSql competencesSql = new CompetencesSql(sql);
        competencesSql.init();

        try {
            Connection con = sql.getConnection();
            Statement statement = con.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS maszyny " +
                            "(" +
                            "id int," +
                            "nazwa char(32)," +
                            "opis char(128)," +
                            "status int," +
                            "wymagania char(256)" +
                            ")");

            System.out.println("test0");

            statement.execute("CREATE TABLE IF NOT EXISTS dupa "+
                    "(" +
                    "id int," +
                    "nazwa char(32)," +
                    "opis char(128)," +
                    "status int," +
                    "wymagania char(256)" +
                    ")");
            statement.close();
            PreparedStatement ps;
            ps = con.prepareStatement("INSERT INTO dupa VALUES(?,?,?,?,?)");
            ps.setInt(1,00440365);
            ps.setString(2,"dmg mori");
            ps.setString(3,"to jest opis");
            ps.setInt(4,0);
            ps.setString(5,"a to sa wymagania\nbrak :D");

            ps.execute();
            ps.close();
            statement = con.createStatement();
            statement.execute("INSERT INTO maszyny SELECT * FROM dupa WHERE dupa.id NOT IN (SELECT id FROM maszyny)");

            ps.close();
            statement = con.createStatement();
            ResultSet result =  statement.executeQuery("SELECT * FROM maszyny");
            while (result.next()) {
                data.add( new data(result.getInt(1),result.getString(2),result.getString(3),
                        result.getInt(4),result.getString(5)));
            }
            statement.execute("DROP TABLE dupa");
        }
        catch (SQLException e) {
            System.out.println("exxception " + e);
        }

    }






    protected void initLeftMachinePane(Container main) {
        main.add(new JButton("1"));
        main.add(new JButton("2"));
        main.add(new JButton("2"));

    }
}
