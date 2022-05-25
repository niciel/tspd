package com.gmail.damianmajcherq.tspd.awt;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Consumer;

public abstract class CachedTableModel extends AbstractTableModel {




    /***
     * first integer begining of copy starts at 0 = first row from copied table
     * secontd integer exclusive end
     */

    private List<ColumnBehaviour> columns;
    private Timer syncRepeating;


    private int rowCountCache =64;

    private Object[][] dataCache;

    /**lower array index of min row from table*/
    private int minDataIndex=0;
    /**lower row from table*/
    private int minRowIndex=-1;

    /** upper array index of max row from table*/
    private int maxDataIndex=0;
    /** upper row from table*/
    private int maxRowIndex=-1;



    public CachedTableModel(int cache, @NotNull List<ColumnBehaviour> columns) {
        this.columns = columns;
        this.dataCache = new Object[cache][];
        this.syncRepeating = new Timer(1000,this::syncOperationEDT);
        this.syncRepeating.start();
        this.toFetch = new int[fetchInitialSize];
    }



    private int fetchInitialSize = 10;
    private int fetchTimeOut = 1000;


    private int[] toFetch;
    private int toFetchSize = 0;
    private int toFetchZero = 0;
    private boolean fetchFull;

    protected void syncOperationEDT(ActionEvent actionEvent) {
        //TODO to remove
        Rectangle d = view.getViewRect();
        int row = table.rowAtPoint(new Point(0,d.y));
        int max = table.rowAtPoint(new Point(0,d.y + d.height-1));
        System.out.println("dim " + d + " row " + row + " max " + max + " dim ");

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return this.columns.get(columnIndex).isEditable();
    }


    /**
     * @param row
     * @return returns 1 when successfully add index to fetch pull 0  when buffer is fully after added last index, and -1 when is already full and waiting for retrive data
     */
    protected int fetchRow(int row){
        if (this.fetchFull)
            return -1;
        int initialSize = this.fetchInitialSize;
        int size = this.toFetchSize;
        if (size >= initialSize ) {
            this.fetchFull = true;
            return -1;
        }
        int zero = this.toFetchZero;
        int index = size+zero;
        if (index >= initialSize){
            index = size -(initialSize-zero);
        }
        this.toFetch[index] = row;
        this.toFetchSize = size+1;
        if ((size+1) >= initialSize)
            return 0;
        return 1;
    }
    //TODO temporal
    public JViewport view;
    public JTable table;

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        int index = this.cacheIndex(rowIndex);
        if (index >=0) {
            Object[] data = this.dataCache[index];
            if (data == null) {
                System.out.println("NULL");
                return -1;
            }
            return this.dataCache[index][columnIndex];
        }
        else {
            int flag = this.fetchRow(rowIndex);
            if (flag <= 0){
                return "preparing";
            }
            return "fatching";
        }
        /*
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
                    System.out.println("cached " + cacheIndex);
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
                    int sign = sign(this.maxDataIndex);
                    this.maxRowIndex += delta;
                    this.maxDataIndex += delta;
                    if (this.maxDataIndex >= this.rowCountCache) {
                        this.maxDataIndex = (this.maxDataIndex - this.rowCountCache);
                        //minim is bigger than max thus needs shifting
                        if (this.maxDataIndex < this.minDataIndex) {
                            if ((this.maxRowIndex - this.minRowIndex) >= this.rowCountCache){

                                //przesuniecie
                                System.out.println("mniejszy ? " +maxRowIndex + ",m " + minRowIndex );
                            }
                        }
                    }

                }
            }
            else {
                System.out.println("DECREMENT");//TODO
            }
            System.out.println("getting ");
            return this.getWithoutFetching(rowIndex,columnIndex);
        }
         */
    }



    public Object getWithoutFetching(int row,int column){
        int index = this.cacheIndex(row);
        System.out.println("without " + row + " dataindex " + index);
        if (index >= 0)
            return this.dataCache[index][column];
        return null;
    }

    /***
     *
     * @param tableRow
     * @return -1 if not cached 0>= return <dataCache
     */
    public int cacheIndex(int tableRow ) {
        if (tableRow >= this.minRowIndex && tableRow <= this.maxRowIndex) {
            int index = this.minDataIndex + (this.minRowIndex - tableRow);
            if (index > this.rowCountCache) {
                index = index - this.rowCountCache;
            }
            return index;
        }
        return -1;
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
        return this.columns.get(columnIndex).dataType;
    }


    @Override
    public String getColumnName(int column) {
        if (column < this.columns.size())
            return this.columns.get(column).name;
        return "";
    }

    protected static int sign(int i) {
        return ((i & 0x80000000) == 0x80000000) ? -1:1;
    }

}
