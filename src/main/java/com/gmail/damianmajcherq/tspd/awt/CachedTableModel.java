package com.gmail.damianmajcherq.tspd.awt;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.lang.ref.WeakReference;
import java.util.List;

public abstract class CachedTableModel extends AbstractTableModel {




    /***
     * first integer begining of copy starts at 0 = first row from copied table
     * secontd integer exclusive end
     */

    private List<ColumnBehaviour> columns;

    private int rowCountCache =64;

    private Object[][] dataCache;

    // index of table
    private int minDataIndex=0;
    // index of table row
    private int minRowIndex=-1;

    private int maxDataIndex=0;
    private int maxRowIndex=-1;



    public CachedTableModel(int cache, @NotNull List<ColumnBehaviour> columns) {
        this.columns = columns;
        this.dataCache = new Object[cache][];
    }



    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return this.columns.get(columnIndex).isEditable();
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= this.minRowIndex && rowIndex <= this.maxRowIndex){
            int index = this.minDataIndex + (this.minRowIndex - rowIndex);
            if (index > this.rowCountCache) {
                index = index - this.rowCountCache;
            }
            return this.dataCache[index][columnIndex];
        }

        int fetchStart;
        int fetchEnd = rowIndex;
        boolean increment;
        if (rowIndex > this.maxRowIndex) {
            fetchStart = this.maxRowIndex + 1;
            increment = true;
        }
        else {
            fetchStart = this.minRowIndex-1;
            increment = false;
        }
        Object[][] data = new Object[Math.abs(fetchEnd-fetchStart)][];

        if (fetchData(Math.min(fetchStart,fetchEnd),Math.max(fetchStart,fetchEnd),data)) {
            if (increment) {
                for(int i = 0 ; i < data.length ; i++) {
                    int cacheIndex = this.maxDataIndex+1+i;
                    if (cacheIndex < this.rowCountCache) {
                        this.dataCache[cacheIndex] = data[i];
                    }
                    else
                        this.dataCache[cacheIndex - this.rowCountCache] = data[i];
                }
                if (this.minRowIndex < 0) {
                    this.minRowIndex = 0;
                    this.maxRowIndex = fetchEnd-1;
                    this.minDataIndex = 0;
                    this.maxDataIndex = fetchEnd-1;
                }
                else {
                    int delta = fetchEnd -fetchStart;

                }
            }
            else {
                System.out.println("DECREMENT");//TODO
            }
            return this.getWithoutFetching(rowIndex,columnIndex);
        }

        return "ERROR";
    }

    public Object getWithoutFetching(int row,int column){
        if (row >= this.minRowIndex && row <= this.maxRowIndex){
            int index = this.minDataIndex + (this.minRowIndex - row);
            if (index > this.rowCountCache) {
                index = index - this.rowCountCache;
            }
            return this.dataCache[index][column];
        }
        return null;
    }

    public @NotNull  ColumnBehaviour getBehaviour(int index) {
        return this.columns.get(index);
    }


    /***
     *
     * @param lowestRow index wiersza łacznie z danymi pod tym indexem
     * @param highestRow index wiersza bez niego (cos jak string split)
     * @param data
     * @return
     */
    public abstract boolean fetchData(int lowestRow, int highestRow, @NotNull final Object[][] data);

    @Override
    public abstract int getRowCount() ;

    @Override
    public int getColumnCount() {
        return this.columns.size();
    }



    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.columns.get(columnIndex).getDataType();
    }


    @Override
    public String getColumnName(int column) {
        if (column < this.columns.size())
            return this.columns.get(column).name;
        return "";
    }
}
