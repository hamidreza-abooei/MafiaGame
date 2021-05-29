package org.ap.midterm.ui;

import org.ap.midterm.Models.GameManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{
    // Fields
    private int clientID;
    private Socket connection;
    private String username;
    GameManager gameManager;
    SharedInformation sharedInformation;
    public ClientHandler(Socket connection , int clientID, GameManager gameManager, SharedInformation sharedInformation){
        this.clientID = clientID;
        this.connection = connection;
        this.gameManager = gameManager;
        this.sharedInformation = sharedInformation;
    }

    /**
     * Run method from Runnable Interface
     */
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(connection.getInputStream());
             DataOutputStream out = new DataOutputStream(connection.getOutputStream())){
//            System.out.println("client handler is running");
            do{
//                System.out.println("Enter username");
                out.writeUTF("Enter Username");
                out.writeUTF("read");
                username = in.readUTF();
//                System.out.println("username: " + username);
            }while (!userNameChecker(username));
            out.writeUTF("Welcome to the Mafia Game " + username);
            out.writeUTF("Wait for the other players to join.");
//            Thread.sleep(180000);
//            Thread.sleep(60000);
            sharedInformation.addClient();
            sharedInformation.waitForJoiningAllMembers();
            out.writeUTF("Game has been started");
            while (true){

                String input = in.readUTF();
                if( input.equalsIgnoreCase("end")){
                    break;
                }
            }
            System.out.println("Server closed [Client-" + clientID + "] connection");

        } catch (IOException e) {
            System.err.println("there is problem in I/O");
        }
    }
    public boolean userNameChecker(String username){
//        System.out.println("username checker");
        return gameManager.checkUsername(username);
    }
}
