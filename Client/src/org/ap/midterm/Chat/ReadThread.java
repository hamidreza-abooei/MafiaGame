package org.ap.midterm.Chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Hamidreza Abooei
 */
public class ReadThread implements Runnable{

    private Socket connection ;
    private ChatClient server;

    /**
     * constructor
     * @param connection for read other clients messages
     */
    public ReadThread(Socket connection , ChatClient server){
        this.connection = connection;
        this.server = server;
    }

    /**
     * Run this thread
     */
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(connection.getInputStream())){
            while (true) {
                String read = in.readUTF();
                System.out.println(read);
                if(read.equalsIgnoreCase("stopChatClients")){
                    server.stopWriting();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error in I/O has been occurred");
        }
    }
}
