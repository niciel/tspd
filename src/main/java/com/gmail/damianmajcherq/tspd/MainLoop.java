package com.gmail.damianmajcherq.tspd;


import com.gmail.damianmajcherq.tspd.cm.CompetencesSql;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class MainLoop {

    private static File ExecutionFolder;


    private static MainSystem MAIN;


    public static void main(String args[]){
        System.out.println(System.getenv("APPDATA"));
        //MainLoop loop = new MainLoop();
        MAIN = new MainSystem();
        ExecutionFolder = new File("").getAbsoluteFile();
        MAIN.init(new SqLiteManagement(ExecutionFolder));

    }

    private MainLoop() {

        System.out.println("execution: " + this.ExecutionFolder);

        SqLiteManagement sql = new SqLiteManagement(this.ExecutionFolder);
        sql.initSqlDatabase();

        CompetencesSql competencesSql = new CompetencesSql(sql);
        competencesSql.init();

// :D to zostalo :D
    }






    protected void initLeftMachinePane(Container main) {
        main.add(new JButton("1"));
        main.add(new JButton("2"));
        main.add(new JButton("2"));

    }
}
