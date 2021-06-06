package org.ap.midterm.Chat;

import java.net.Socket;

/**
 * @author Hamidreza Abooei
 */
public class ReadThread implements Runnable{

    private Socket connection ;

    /**
     * constructor
     * @param connection for read other clients messages
     */
    public ReadThread(Socket connection){
        this.connection = connection;
    }

    /**
     * Run this thread
     */
    @Override
    public void run() {

    }
}
