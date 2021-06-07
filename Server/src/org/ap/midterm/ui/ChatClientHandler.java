package org.ap.midterm.ui;

import java.io.*;
import java.net.Socket;

/**
 * @author Hamidreza Abooei
 */
public class ChatClientHandler implements Runnable{
    private Socket connection ;
    private ChatServer server;
    private PrintWriter writer;
    private String username;
    private String rule;

    /**
     * constructor
     * @param connection connection to Client
     */
    public ChatClientHandler(Socket connection , ChatServer server){
        this.connection = connection;
        this.server = server;
    }




    /**
     * run this thread
     */
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(connection.getInputStream());
             DataOutputStream out = new DataOutputStream(connection.getOutputStream())){

            writer = new PrintWriter(out , true);
            username = in.readUTF();
            rule = in.readUTF();
            Thread.sleep(1000);
            server.broadcast(username + " connected." + "[" + rule + "]");
            while (true){
                String clientMessage = in.readUTF();
                String serverMessage = "[" + username + "]: " + clientMessage;
                server.broadcast(serverMessage);
            }



        } catch (IOException e) {
            System.err.println("error has been occurred in I/O.");
        } catch (InterruptedException e) {
            System.err.println("interrupt has been occurred.");
        }
    }

    /**
     * send massage to client
     * @param message to send
     */
    public void sendMessage(String message){
        writer.println(message);
    }

}
