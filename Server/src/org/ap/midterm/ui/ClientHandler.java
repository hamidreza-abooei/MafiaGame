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
    GameStarter gameStarter;
    public ClientHandler(Socket connection , int clientID, GameManager gameManager, GameStarter gameStarter){
        this.clientID = clientID;
        this.connection = connection;
        this.gameManager = gameManager;
        this.gameStarter = gameStarter;
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
                out.writeUTF("read");
                username = in.readUTF();
            }while (!userNameChecker(username));
            out.writeUTF("Welcome to the Mafia Game " + username);
            out.writeUTF("Wait for the other players to join.");
            gameStarter.addClient();
            gameStarter.waitForJoiningAllMembers();
            out.writeUTF("Game has been started");
//            gameManager.play();
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
        return gameManager.checkUsername(username);
    }
}
