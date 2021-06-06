package org.ap.midterm.Chat;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Hamidreza Abooei
 */
public class ChatClient implements Runnable{

    private String host;
    private int port;

    /**
     * constructor
     * @param host host ip address
     * @param port port
     */
    public ChatClient(String host, int port ){
        this.host = host;
        this.port = port;
    }

    /**
     * run this thread
     */
    @Override
    public void run() {
        try (Socket socket = new Socket(host , port)){
            Thread readThread = new Thread(new ReadThread(socket));
            Thread writeThread = new Thread(new WriteThread(socket));
            readThread.start();
            writeThread.start();
        }catch (IOException e){
            System.err.println("Error has been occurred in chat server I/O.");
        }
    }
}
