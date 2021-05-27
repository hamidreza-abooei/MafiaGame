package org.ap.midterm.ui;

import org.ap.midterm.Models.GameManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.PrivilegedExceptionAction;

public class ClientHandler implements Runnable{
    // Fields
    private int clientID;
    private Socket connection;
    private String username;
    GameManager gameManager;
    public ClientHandler(Socket connection , int clientID, GameManager gameManager){
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
            do{
                out.writeUTF("Enter Username");
                username = in.readUTF();
            }while (!userNameChecker(username));

            while (true){
                out.writeUTF("end");
                String input = in.readUTF();
                if( input.equalsIgnoreCase("end")){
                    break;
                }
            }
            System.out.println("Server closed [Client-" + clientID + "] connection");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean userNameChecker(String username){
        return gameManager.checkUsername(username);
    }
}
