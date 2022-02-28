package com.gmail.damianmajcherq.tspd.awt;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class SimpleTableModel extends AbstractTableModel {

    /***
     * first integer begining of copy starts at 0 = first row from copied table
     * secontd integer exclusive end
     */
    private String[] columns;
    private Class[] types;


    public SimpleTableModel(String[] columns , Class[] types) {
        this.columns = columns;

        this.types = types;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            return false;
        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex) ;


    @Override
    public abstract int getRowCount() ;

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.types[columnIndex];
    }




    @Override
    public String getColumnName(int column) {
        if (column < this.columns.length)
            return this.columns[column];
        return "";
    }
}
