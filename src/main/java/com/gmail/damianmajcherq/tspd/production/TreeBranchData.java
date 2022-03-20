package com.gmail.damianmajcherq.tspd.production;

import com.gmail.damianmajcherq.tspd.Utils;
import com.gmail.damianmajcherq.tspd.icons.EmployedIcon;

import javax.swing.*;
import java.awt.*;

public class TreeBranchData {

    protected String name;
    protected long sqlID; // full id of parent
    protected int deep; // for know how deep its is






    public void buildComponents(JTree tree , JPanel component) {
//        JPanel component = new JPanel();
//        FlowLayout l = new FlowLayout();
//        l.setAlignment(0);
//        l.setVgap(0);
//        l.setHgap(0);
//        component.setLayout(l);
//        component.setBackground(null);

        Dimension d;
        int h = Utils.DEFAULT_FONT_SIZE;

        JComponent p = new JLabel(new EmployedIcon(h));
        d = p.getPreferredSize();
        d.height = h;

        p.setPreferredSize(d);
        component.add(p);



        p = new JLabel(new EmployedIcon(h));
        d = p.getPreferredSize();
        d.height = h;
        p.setPreferredSize(d);
        component.add(p);


        p = new JLabel("test");
        d = p.getPreferredSize();
        d.height = h;
        p.setPreferredSize(d);
        component.add(p);

        d = component.getPreferredSize();
        d.height = (int) (Utils.DEFAULT_FONT_SIZE*1.2f);
        component.setPreferredSize(d);

    }

}
