package com.gmail.damianmajcherq.tspd.production;

import com.gmail.damianmajcherq.tspd.icons.EmployedIcon;
import com.gmail.damianmajcherq.tspd.icons.IconUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.List;

public class ProductionTreeRenderer extends DefaultTreeCellRenderer {




    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        //System.out.println("rendererCall " + value  + " selected " + selected + " expanded " + expanded + " leaf " + leaf + " row " + row + " focused " + hasFocus);
        Component comp = super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
        //System.out.println(" dimensin " + comp.getBounds());
        System.out.println(" stringi " + value.getClass());

        if (value instanceof DefaultMutableTreeNode) {
            Object t = ((DefaultMutableTreeNode) value).getUserObject();
            if (t instanceof String){
                return new DefaultTreeNodeComponent(tree);
            }

        }
        return comp;
    }

    public class DefaultTreeNodeComponent extends JPanel {

        private List<Icon> icons;

        public DefaultTreeNodeComponent(JTree tree) {
            Dimension d = getPreferredSize();
            int h = d.height;
            JComponent p = new JLabel(new EmployedIcon(h));
            add(p);
            p = new JLabel(new EmployedIcon(h));
            add(p);
            add(new JLabel("test"));
        }

    }

}
