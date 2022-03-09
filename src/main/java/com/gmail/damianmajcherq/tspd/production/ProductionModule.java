package com.gmail.damianmajcherq.tspd.production;

import com.gmail.damianmajcherq.tspd.ITSPDModule;
import com.gmail.damianmajcherq.tspd.MainModule;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;

public class ProductionModule implements ITSPDModule {


    @Override
    public void preStart(MainModule main) {
        SqlProduction sqp = new SqlProduction(main.GetSqlManagement());
        sqp.init();
    }

    @Override
    public void onStart(MainModule main) {

    }

    @Override
    public void initFrames(MainModule main) {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new TreeNodeData());
        TreeModel model = new DefaultTreeModel(root){

        };
        root.add(new DefaultMutableTreeNode(":O nie tym razem"));
        JTree tree = new JTree(model);
        JPanel container = new JPanel();
        container.add(tree);
        main.registerTabPane("employed", container, null);


    }
}
