package org.ap.midterm.ui;

import java.util.ArrayList;

public class ChatServer {
//    private int port;
    private ArrayList<ClientHandler> clientHandlers;
    public ChatServer(){

    }
    public void addClientHandlers(ArrayList<ClientHandler> clientHandlers){
        this.clientHandlers = clientHandlers;
    }

    public void startChat(int port){
//        this.port = port;

    }
    private void broadCast(){

    }
}
