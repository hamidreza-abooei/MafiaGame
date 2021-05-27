package org.ap.midterm.ui;

import org.ap.midterm.Models.GameManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    GameManager gameManager;
    private boolean running;

    public Server(int port){
        this.running = true;
    }

    public void startServer( int port ){
        ExecutorService pool = Executors.newCachedThreadPool();
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server with port : " + port + " Started \nWaiting for Client .....");
            int clientNumber = 1;
            while (running){
                pool.execute(new ClientHandler(serverSocket.accept(),clientNumber));
                System.out.println("Server connected to new Client [Client-" + clientNumber + "]");
                clientNumber++;
            }
            pool.shutdown();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
    public void downServer(){
        this.running = false;
    }

}
