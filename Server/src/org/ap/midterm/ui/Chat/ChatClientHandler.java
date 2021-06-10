package org.ap.midterm.ui.Chat;

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
        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()){

            writer = new PrintWriter(out , true);
//            System.out.println("chat client initiated. waiting for username...");
//            username = in.readUTF();
//            System.out.println("username: " + username);
//            System.out.println("waiting for rule...");
//            rule = in.readUTF();
//            System.out.println("rule: " + rule);
            System.out.println("send message");
            writer.println("hello");
            System.out.println("message has been sent");
//            System.out.println(in.read());
//            System.out.println("message readed");
//            System.out.println(in.read());
            String readString = "";
            while (true){
                int readChar = in.read();
                if (readChar == '\n'){
                    System.out.println(readString);
                    server.broadcast(readString);
                    readString = "";
                }
                readString += (char) readChar;
//                System.out.print(Character.toString((char) readChar));


            }
//            Thread.sleep(1000);
//            server.broadcast(username + " connected." + "[" + rule + "]");
//            while (true){
//                String clientMessage = in.readUTF();
//                String serverMessage = "[" + username + "]: " + clientMessage;
//                server.broadcast(serverMessage);
//            }



        } catch (IOException e) {
            System.err.println("error has been occurred in I/O.");
        } //catch (InterruptedException e) {
//            System.err.println("interrupt has been occurred.");
//        }
    }

    /**
     * send massage to client
     * @param message to send
     */
    public void sendMessage(String message){
        writer.println(message);
    }

}
