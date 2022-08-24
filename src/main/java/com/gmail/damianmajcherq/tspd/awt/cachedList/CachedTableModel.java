package com.gmail.damianmajcherq.tspd.awt.cachedList;

import com.gmail.damianmajcherq.tspd.awt.ColumnBehaviour;
import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


//TODO calkiem osobna klasa kontener z JScrool JTable
public abstract class CachedTableModel extends AbstractTableModel  {

    private static final float CACHE_SIZE_MULTIPLIER = 2;


    /***
     * first integer begining of copy starts at 0 = first row from copied table
     * secontd integer exclusive end
     */
    private List<ColumnBehaviour> columns;


    private int minimumCacheSize;
    private LinkedList<CacheBucket> cache;
    /***
     * external database list size !
     */
    private int totalRowCount = 0;


    public CachedTableModel(@NotNull List<ColumnBehaviour> columns) {
        this.columns = columns;
        this.cache = new LinkedList<>();
    }

    private int lastRowMin = 0;
    private int lastRowMax = 0;



    public void tickTackToe(){
        System.out.println("update");

        /*
        this.cache.stream().forEach(c -> {
            if (c.status == 0) {
                Future f = c.worker;
                if (f.isDone()) {
                    if (f.isCancelled()) {
                        c.status = -1;
                    }
                    else {
                        c.status = 1;
                        try {
                            c.list = (Object[][]) f.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        fireTableRowsUpdated(c.lowestRowIndex,c.lowestRowIndex+c.elements);
                    }
                }
            }
        });
         */
    }


    /***
     * called from CachedTable class multipleTimes perSecond
     * @param min index of the max upper element visible in view
     * @param max index of the max lower element visible in view
     */
    protected void UpdateViewSize(int min,int max) {




        /*
        int size = max - min;
        this.lastRowMin = min;
        this.lastRowMax = max;
        if (this.minimumCacheSize < size) {
            this.minimumCacheSize = (int) (size*CACHE_SIZE_MULTIPLIER);
            if (!this.cache.isEmpty()) {
                //TODO dispose

            }
            reloadCache(this.minimumCacheSize);
        }
        else {
            System.out.println("else " +this.cache.size());
            CacheBucket b = this.cache.peekFirst();
            if (b == null) {
                b = addBucket(min,max);
                //this.schedule(b.worker);
                return;
            }

            if (min < b.lowestRowIndex) {
                int count = b.lowestRowIndex-min;
                System.out.println("low  " + count);

                if (count <= 0) {
//                        TODO
                    return;
                }
                b = this.addBucket(min,count);
                if (b == null) {
//                        TODO
                    return;
                }
            }
            else {

                b = this.cache.peekLast();
                int begining = b.lowestRowIndex+b.elements;
                int count = max - begining +1;
                if (count <= 0){
//                        TODO
                    return;
                }
                b = this.addBucket(begining,count);
                if (b == null) {
//                    TODO
                    return ;
                }
            }
            //this.schedule(b.worker);
        }
         */
    }

    protected void reloadCache(int newCacheSize) {
        this.cache.stream().forEach(cb -> cb.close());
        this.cache.clear();

        CacheBucket cb = addBucket(this.lastRowMin,this.lastRowMax-this.lastRowMin +1);
        if (cb == null) {
//            TODO
            return;
        }
        //this.schedule(cb.worker);
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CacheBucket b = foundAtRow(rowIndex);
        if (b == null) {
            return "caching";
        }
        if (b.GetFlags() >=1) {
            int i = rowIndex- b.lowestRowIndex ;
            // b.list[i][columnIndex];
            return null;
        }
        else {
            return null;
        }
    }

    protected CacheBucket addBucket(int min,int count){
        int max = min + count;
        CacheBucket bc = this.cache.peekFirst();
        boolean head;
        if (bc == null || max < bc.lowestRowIndex) {
            head = true;
            bc = new CacheBucket(new FutureTask<>(fetchData(min,count)),min,count);
            this.cache.addFirst(bc);
        }
        else {
            bc = this.cache.peekLast();
            if (max >= bc.lowestRowIndex + bc.elements){
                bc = new CacheBucket(new FutureTask<>(fetchData(min,count)),min,count);
                this.cache.addLast(bc);
                head = false;
            }
            else
                return null;
        }
        if (head) {

        }
        return bc;
    }



    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return this.columns.get(columnIndex).isEditable();
    }








    protected CacheBucket foundAtRow(int row){
        return this.cache.stream().filter(p-> {
            int i = p.lowestRowIndex;
            if (row >= i && row < i+p.elements) return true;return false;}).findFirst().orElse(null);
    }


    public @NotNull  ColumnBehaviour getBehaviour(int index) {
        return this.columns.get(index);
    }


    /***
     *
     * @return
     */
    public abstract boolean schedule(FutureTask<Object[][]> task);
    public abstract Callable<Object[][]> fetchData(int lowestRow, int count);
    public abstract int fetchRowCount();


    @Override
    public int getRowCount() {
        return this.totalRowCount;
    }



    public void fireRowCountChanged() {
        int last = totalRowCount;
        this.totalRowCount = fetchRowCount();
    }

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
