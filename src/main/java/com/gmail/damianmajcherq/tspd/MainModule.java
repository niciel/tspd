package com.gmail.damianmajcherq.tspd;

import com.gmail.damianmajcherq.tspd.cm.CompetenceMatrix;
import com.gmail.damianmajcherq.tspd.tags.TagModule;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

public class MainModule {


    private SqLiteManagement sql;
    private List<ITSPDModule> modules;
    private JTabbedPane tabPane;
    public JFrame mainFrame;


    private JMenu modulesMenu;
    private JMenuBar menuBar;

    public MainModule (){
        this.modules = new ArrayList<>();
        this.modules.add(new CompetenceMatrix());
        this.modules.add(new TagModule());
    }


    public void init(SqLiteManagement sql){
        this.sql = sql;
        this.sql.initSqlDatabase();
        for (ITSPDModule m : this.modules)
            m.preStart(this);

        initJFrames();

    }

    private void initJFrames(){

        this.mainFrame = new JFrame("Tspd");

        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.menuBar = new JMenuBar();
        this.mainFrame.setJMenuBar(this.menuBar);
        this.modulesMenu = new JMenu("modules");
        this.menuBar.add(this.modulesMenu);
        this.tabPane = new JTabbedPane();
        this.mainFrame.add(this.tabPane);








        for (ITSPDModule m : this.modules)
            m.initFrames(this);



        tabPane.addTab("test2",new JPanel());





        this.mainFrame.setVisible(true);

    }

    public SqLiteManagement GetSqlManagement(){
        return this.sql;
    }

    /***
     *
     * @param tab
     * @param c
     * @return
     */
    public void registerTabPane(String tab, Container c ,Icon icon ) {
       this.tabPane.addTab(tab,icon,c);
    }


    public void registerModuleMenuItem(JMenuItem item) {
        this.modulesMenu.add(item);
    }

}