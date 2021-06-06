package org.ap.midterm.Chat;

import java.io.DataInputStream;
import java.io.IOException;
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
        try (DataInputStream in = new DataInputStream(connection.getInputStream())){
            System.out.println(in.readUTF());
        } catch (IOException e) {
            System.err.println("Error in I/O has been occurred");
        }
    }
}
