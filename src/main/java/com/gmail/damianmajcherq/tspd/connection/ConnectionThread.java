package com.gmail.damianmajcherq.tspd.connection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionThread extends Thread {


    private BlockingQueue<ConnectionTask> queue;
    private Connection connection;
    //private int connectionAttempts =3;

    private boolean terminate;

    private ConnectionThread (){
        this.queue = new LinkedBlockingDeque<>();

    }


    protected Connection establishConnection() throws SQLException {
        //TODO
        return null;
    }

    @Override
    public void run() {

        mainloop :
        while (this.terminate) {
            ConnectionTask task = null;
            try {
                task = queue.take();
            } catch (InterruptedException e) {
                //TODO should never attempts
                e.printStackTrace();

            }
            try {
                connected : // could make never ending connection without connection
                if (connection.isClosed()) {
                    try {
                        connection = establishConnection();
                        // connection validation
                        break connected;
                    }
                    catch (SQLException es) {
//                        TODO
                    }
                    this.terminate = true;
                    break mainloop;
                }
                else {
                    try {
                        task.executeQuerry(connection);
                    }catch (SQLException e){
                        SqlOutputData<?> out = new SqlOutputData<>(task.getResult(),task.isFail());
                        //SwingUtilities.invokeLater(() -> {task.edtSync.accept(out);});
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}














