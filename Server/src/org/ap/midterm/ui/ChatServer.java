package org.ap.midterm.ui;

import java.util.ArrayList;
/**
 * @author Hamidreza Abooei
 */
public class ChatServer {
//    private int port;
    private ArrayList<ClientHandler> clientHandlers;

    /**
     * constructor
     */
    public ChatServer(){

    }

    /**
     * add a client to chat room
     * @param clientHandlers client
     */
    public void addClientHandlers(ArrayList<ClientHandler> clientHandlers){
        this.clientHandlers = clientHandlers;
    }

    /**
     * start server
     * @param port port to connect client
     */
    public void startChat(int port){
//        this.port = port;

    }

    /**
     * broadcast message to other clients in the chat room
     */
    private void broadCast(){

    }
}
