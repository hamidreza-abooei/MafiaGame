package org.ap.midterm.Chat;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Hamidreza Abooei
 */
public class ChatClient implements Runnable{

    private final String host;
    private final int port;
    private WriteThread writeThread;
    private String username;
    private String rule;

    /**
     * constructor
     * @param host host ip address
     * @param port port
     * @param rule rule
     * @param username username
     */
    public ChatClient(String host, int port  , String username , String rule){
        this.host = host;
        this.port = port;
        this.username = username;
        this.rule = rule;
    }

    /**
     * run this thread
     */
    @Override
    public void run() {
        try (Socket socket = new Socket(host , port)){
            Thread readThread = new Thread(new ReadThread(socket , this));
            writeThread = new WriteThread(socket , username , rule);
            Thread write = new Thread(writeThread);
            readThread.start();
            write.start();
        }catch (IOException e){
            System.err.println("Error has been occurred in chat server I/O.");
        }
    }
    public void stopWriting(){
        writeThread.stopThisThread();
    }

}
