package com.gmail.damianmajcherq.tspd.production;

import com.gmail.damianmajcherq.tspd.ITSPDModule;
import com.gmail.damianmajcherq.tspd.MainSystem;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class ProductionModule implements ITSPDModule {


    @Override
    public void preStart(MainSystem main) {
        SqlProduction sqp = new SqlProduction(main.getSqlManagement());
        sqp.init();
    }

    @Override
    public void onStart(MainSystem main) {

    }

    @Override
    public void initFrames(MainSystem main) {

        JTree tree = new JTree();
        GroupTreeController controller = new GroupTreeController(tree);
        JPanel container = new JPanel();

        container.add(tree);
        main.registerTabPane("employed", container, null);


    }
}
