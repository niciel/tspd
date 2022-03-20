package com.gmail.damianmajcherq.tspd.production;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GroupTreeController implements MouseListener {

    public final WeakReference<JTree> tree;

    private List<ILeafController> leafsController;


    public GroupTreeController(JTree tree) {
        this.leafsController = new ArrayList<>();
        this.tree = new WeakReference<>(tree);

        tree.addMouseListener(this);

        WeakReference<GroupTreeController> _instance = new WeakReference<>(this);
        tree.setCellRenderer(new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                          boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value instanceof DefaultMutableTreeNode) {
                    Object data = ((DefaultMutableTreeNode) value).getUserObject();
                    if (data == null) {
//                        TODO this sohuldnt hava place
                    }
                    else if (data instanceof TreeBranchData) {
//                        TODO branch data
                    }
                    else if (leaf){
                        ILeafController c = _instance.get().findController(data);
                        if (c != null) {
                            Component r = c.getLeaf(data);
                            if (r != null)
                                return r;
                        }
                    }
                }
                return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            }
        });
        tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("all")) {

            @Override
            public Object getChild(Object parent, int index) {
                int i = super.getChildCount(parent);
                if (index > i) {
                    return super.getChild(parent,index);
                }
                else {
                    return _instance.get().getChild(parent, index, i);
                }
            }

            @Override
            public int getChildCount(Object parent) {
                int i = super.getChildCount(parent);
                if (parent instanceof DefaultMutableTreeNode) {
                    Object data = ((DefaultMutableTreeNode) parent).getUserObject();
                    if (data instanceof TreeBranchData) {
                        i += _instance.get().getLeafsCount((TreeBranchData) data);
                    }
                }
                return i;
            }
        });

    }

    public Object getChild(Object parent , int index,int parentIndex) {
        if (parent instanceof DefaultMutableTreeNode) {
            Object data = ((DefaultMutableTreeNode) parent).getUserObject();
            if (data instanceof TreeBranchData) {
                TreeBranchData branch = (TreeBranchData) data;
                int search = index - parentIndex;
                int previousIndex = 0;
                for (ILeafController c : this.leafsController) {
                    if (c.hashBranchAccess(branch)){
                        int count = c.getCount(branch);
                        if ( search < (previousIndex + count)){
                            return c.get(branch,search - previousIndex);
                        }
                        else {
                            previousIndex += count;
                        }
                    }
                }
            }
        }
//        TODO
        return null;
    }

    public int getLeafsCount(TreeBranchData branch) {
        int total = 0;
        for (ILeafController c : this.leafsController) {
            if (c.hashBranchAccess(branch)) {
                total += c.getCount(branch);
            }
        }
        return total;
    }


    public ILeafController findController(Object o) {
        for (ILeafController c : this.leafsController) {
            if (c.controlType(o))
                return c;
        }
        return null;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {    }

    @Override
    public void mousePressed(MouseEvent e) {    }

    @Override
    public void mouseEntered(MouseEvent e) {    }

    @Override
    public void mouseExited(MouseEvent e) {    }
}
