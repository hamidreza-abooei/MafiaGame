package org.ap.midterm.ui;

import org.ap.midterm.Models.GameManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * @author Hamidreza Abooei
 */
public class ClientHandler implements Runnable{
    // Fields
    private int clientID;
    private Socket connection;
    private String username;
    private GameManager gameManager;
    private GameStarter gameStarter;
    private ArrayList<String> message;

    /**
     * Constructor
     * @param connection connection to client
     * @param clientID the client number
     * @param gameManager the game manager
     * @param gameStarter the game starter
     */
    public ClientHandler(Socket connection , int clientID, GameManager gameManager, GameStarter gameStarter){
        this.message = new ArrayList<>();
        this.clientID = clientID;
        this.connection = connection;
        this.gameManager = gameManager;
        this.gameStarter = gameStarter;
    }

    /**
     * Run method from Runnable Interface
     */
    @Override
    public synchronized void run() {
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
            while (true){
                String input = writeToClient();
                out.writeUTF(input);
                if (input.equalsIgnoreCase("read")){
                    String readOfClient = in.readUTF();
                    readFromClient(readOfClient);
                }
                if( input.equalsIgnoreCase("end")){
                    break;
                }
            }
            System.out.println("Server closed [Client-" + clientID + "] connection");

        } catch (IOException e) {
            System.err.println("there is problem in I/O");
        }
    }

    /**
     * check name that is not repeated
     * @param username the player(client) username
     * @return it is repetitive or not
     */
    public boolean userNameChecker(String username){
        return gameManager.checkUsername(username,this);
    }


    /**
     * get message from game and put to message param to write in to client
     * @param message get message from game
     */
    public synchronized void startWriting(String message){
        this.message.add(message);
        notify();
        try {
            wait();
        } catch (InterruptedException e) {
            System.out.println("message didn't sent, interrupted");
        }
    }

    /**
     * write message to client
     * @return message to send to client
     */
    private synchronized String writeToClient(){
        try {
            notify();
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String send = this.message.get(0);
        this.message.remove(0);
        return send;
    }

    /**
     * read message from client
     * @param string message from client
     */
    public void readFromClient(String string){
        gameManager.readFromClient(string);
    }

    /**
     * get connection socket
     * @return connection socket
     */
    public Socket getConnection() {
        return connection;
    }

    /**
     * get username
     * @return username
     */
    public String getUsername(){
        return username;
    }
}
