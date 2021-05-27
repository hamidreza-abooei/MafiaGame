package org.ap.midterm.ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{
    // Fields
    private int clientID;
    private Socket connection;
    public ClientHandler(Socket connection , int clientID){
        this.clientID = clientID;
        this.connection = connection;
    }

    /**
     * Run method from Runnable Interface
     */
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(connection.getInputStream());
             DataOutputStream out = new DataOutputStream(connection.getOutputStream())){
            while (true){
                String input = in.readUTF();
                if( input.equalsIgnoreCase("end")){
                    break;
                }
            }
            System.out.println("Server closed	 [Client-" + clientID + "] connection");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
