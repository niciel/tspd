package com.gmail.damianmajcherq.tspd.connection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConnectionTask implements IConnectionJob{

    private boolean fail;
    private Object input;


    public final BiFunction<IConnectionJob,Connection,Object> async;
    public final Consumer<SqlOutputData<?>> edtSync;

    public ConnectionTask(BiFunction<IConnectionJob, Connection, Object> async, Consumer<SqlOutputData<?>> edtSync) {
        this.async = async;
        this.edtSync = edtSync;
    }

    public Object getResult()  {
        return input;
    }

    public void executeQuerry(Connection connection) throws SQLException {
        this.input = async.apply(this,connection);
    }

    public boolean isFail(){
        return this.fail;
    }

    @Override
    public void setFail(boolean fail) {
        if (fail)
            this.fail = true;
    }
}
