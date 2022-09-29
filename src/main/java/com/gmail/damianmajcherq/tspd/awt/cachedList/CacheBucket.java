package com.gmail.damianmajcherq.tspd.awt.cachedList;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.concurrent.FutureTask;

public class CacheBucket  {

    public CacheBucket(@NotNull FutureTask<Object[][]> task, int lowestRowIndex , int count) {
        this.elements = count;
        this.lowestRowIndex = lowestRowIndex;
        this.worker = task;
    }

    public Object data[][] ;
    public final int lowestRowIndex;
    public final int elements;

    private byte flags = 0;//-1 fail 0 working 1 ends
    private boolean working = false;
    private FutureTask<Object[][] > worker;

    protected void invokeTask() {

    }


    public void check(){
        if (this.flags != 0) {
            if (worker.isDone())
                flags = 1;
            else if (worker.isCancelled())
                flags = -1;
        }
    }

    public byte GetFlags() {
        return this.flags;
    }

    public void close() {
        if (flags != 1) {
            if (worker != null)
                worker.cancel(true);
        }
    }
}
