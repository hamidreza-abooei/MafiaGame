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
    private boolean rulePrint;

    /**
     * constructor
     * @param connection connection to Client
     */
    public ChatClientHandler(Socket connection , ChatServer server , boolean rulePrint){
        this.connection = connection;
        this.server = server;
        this.rulePrint = rulePrint;
    }




    /**
     * run this thread
     */
    @Override
    public synchronized void run() {
        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()){

            writer = new PrintWriter(out , true);
            // get username
            username = "";
            while (true){
                int readChar = in.read();
                if (readChar == '\r'){
                    in.read(); //for removing \n
                    break;
                }
                username += (char) readChar;
            }
            // get rule
            rule = "";
            while (true){
                int readChar = in.read();
                if (readChar == '\r'){
                    in.read(); // for removing \n
                    break;
                }
                rule += (char) readChar;
            }
            //send welcoming message
            String welcomeMessage = "Chat has been started " + username + " your rule is: " + rule;
            writer.println(welcomeMessage);
            String readString = "";
            //read and write
            while (true){
                int readChar = in.read();
                if (readChar == '\r'){
                    in.read(); // for removing \n

                    if (rulePrint)
                        server.broadcast("[" + username + ":" + rule + "]: " + readString);
                    else
                        server.broadcast("[" + username + "]: " + readString);

                    readString = "";
                }else {
                    readString += (char) readChar;
                }
            }

        } catch (IOException e) {
            System.err.println("error has been occurred in I/O.");
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
