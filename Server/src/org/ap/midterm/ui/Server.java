package org.ap.midterm.ui;
import org.ap.midterm.Models.GameManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    // Fields
    GameManager gameManager;
    private boolean running;

    /**
     * Constructor
     */
    public Server(){
        this.running = true;
        gameManager = new GameManager(10);
    }

    /**
     * Start server and waiting for clients
     * @param port the port that server running on
     */
    public void startServer( int port ){
        ExecutorService pool = Executors.newCachedThreadPool();
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server with port : " + port + " Started \nWaiting for Client .....");
            int clientNumber = 1;
            while (running){
                pool.execute(new ClientHandler(serverSocket.accept(),clientNumber , gameManager));
                System.out.println("Server connected to new Client [Client-" + clientNumber + "]");
                clientNumber++;
            }
            pool.shutdown();
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (NullPointerException e){
            System.err.println(e.toString());
        }
    }

    /**
     * Down server
     */
    public void downServer(){
        this.running = false;
    }

}
