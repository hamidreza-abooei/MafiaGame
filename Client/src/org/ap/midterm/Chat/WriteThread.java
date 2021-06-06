package org.ap.midterm.Chat;

import java.net.Socket;

/**
 * @author Hamidreza Abooei
 */
public class WriteThread implements Runnable{
    private Socket socket;

    /**
     * constructor
     * @param socket socket to write massage in chat room
     */
    public WriteThread(Socket socket){
        this.socket = socket;
    }
    /**
     * Run this thread
     */
    @Override
    public void run() {

    }
}
