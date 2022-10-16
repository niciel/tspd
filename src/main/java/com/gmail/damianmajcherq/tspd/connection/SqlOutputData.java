package com.gmail.damianmajcherq.tspd.connection;

public class SqlOutputData<T> {

    public final T data;
    public final boolean fail;


    public SqlOutputData(T data, boolean fail) {
        this.data = data;
        this.fail = fail;
    }


}
