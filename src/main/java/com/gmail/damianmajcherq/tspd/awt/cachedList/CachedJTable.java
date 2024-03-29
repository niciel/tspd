package com.gmail.damianmajcherq.tspd.awt.cachedList;

import com.gmail.damianmajcherq.tspd.awt.ColumnBehaviour;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CachedJTable extends JTable {


    private CachedCellRenderer renderer;


    public CachedJTable(CachedTableModel model) {
        super();
        this.renderer = new CachedCellRenderer();
        this.setModel(model);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return renderer;
    }



    private class CachedCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            CachedTableModel model = (CachedTableModel) table.getModel();
            ColumnBehaviour bh = model.getBehaviour(column);
            Container c = bh.getView(value,isSelected,hasFocus);
            return c != null ? c: super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        }
    }
}
