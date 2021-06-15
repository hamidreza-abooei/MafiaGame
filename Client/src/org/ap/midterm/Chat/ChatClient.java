package org.ap.midterm.Chat;

import java.io.*;
import java.net.Socket;

/**
 * @author Hamidreza Abooei
 */
public class ChatClient implements Runnable{

    private final String host;
    private final int port;
    private String username;
    private String rule;
    private PrintWriter writer;

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
    public synchronized void run() {
        MessageWriter messageWriter = new MessageWriter(this);
        Thread messageWriterThread = new Thread(messageWriter);
        messageWriterThread.start();
        try (Socket socket = new Socket(host , port)){
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output , true);

            // server client configuration
            writer.println(username);
            writer.println(rule);

            // read messages
            String readString;
            while (true){
                readString ="";
                int read ;
                do {
                    read = input.read();
                    readString += (char) read;
                } while ((char) read != '\n');
                if (readString.equalsIgnoreCase("stopChatClients\r\n")){
//                    System.out.println("here");
                    messageWriter.stopWriting();

                    break;
                }
                System.out.print(readString);

            }
            input.close();
            output.close();
            socket.close();
            System.out.println("close all");
            System.out.println("type some thing to resume");

        }catch (IOException e){
            System.err.println("Error has been occurred in chat server I/O.");
        }
    }

    /**
     * Send message from console to server
     * @param message to be sent
     */
    public void putMessage(String message){
        writer.println(message);
    }

}
