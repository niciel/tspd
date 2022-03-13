package com.gmail.damianmajcherq.tspd.tags;

import com.gmail.damianmajcherq.tspd.ITSPDModule;
import com.gmail.damianmajcherq.tspd.MainSystem;

import javax.swing.*;

public class TagModule implements ITSPDModule {


    private SqlTagSearch sql;

    @Override
    public void preStart(MainSystem main) {
        this.sql = new SqlTagSearch(main.getSqlManagement());
        this.sql.init();
    }

    @Override
    public void onStart(MainSystem main) {
        
    }

    @Override
    public void initFrames(MainSystem main) {
        JMenuItem item = new JMenuItem("tag editor");
        main.registerModuleMenuItem(item);
        item.addActionListener( e-> {
            TagDialog dialog = new TagDialog(main.mainFrame , sql);
            dialog.setVisible(true);

            /*
            JPanel cont = new JPanel();

            JOptionPane.showConfirmDialog(null,cont,"test",JOptionPane.CLOSED_OPTION);

            //JOptionPane cont = new JOptionPane();
            GridBagLayout gbl = new GridBagLayout();
            gbl.rowWeights = new double[]{0.2d,0.8d};
            gbl.columnWeights = new  double[]{0.2d,0.8d};
            cont.setLayout(gbl);

            GridBagConstraints i = new GridBagConstraints();
            i.gridx = 0;
            i.gridy = 1;
            i.weightx = 2;
            i.weighty = 1;
            i.fill = GridBagConstraints.BOTH;

            JTable table = new JTable(){

            };
            JScrollPane scroll = new JScrollPane(table);
            cont.add(scroll , i);


             */

        });
    }
}
