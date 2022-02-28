package com.gmail.damianmajcherq.tspd.awt;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class SqlCellRenderer implements TableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        return new Container() {
            @Override
            public void removeNotify() {
                super.removeNotify();
            }
        };
    }
}
