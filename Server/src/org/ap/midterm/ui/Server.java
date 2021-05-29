package org.ap.midterm.ui;
import org.ap.midterm.Models.GameManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

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
        SharedInformation sharedInformation = new SharedInformation();
        ExecutorService pool = Executors.newCachedThreadPool();
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server with port : " + port + " Started \nWaiting for Client .....");
            int clientNumber = 1;
            ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
            while (running){
                clientHandlers.add(new ClientHandler(serverSocket.accept(),clientNumber , gameManager , sharedInformation));
//                System.out.println(clientHandlers.get(clientNumber-1));
                pool.execute(clientHandlers.get(clientNumber-1));
//                pool.execute(new ClientHandler(serverSocket.accept(),clientNumber , gameManager));
                System.out.println("Server connected to new Client [Client-" + clientNumber + "]");
                clientNumber++;
                if(clientNumber > 3){
                    downServer();
                }
            }
//            pool.notifyAll();
//            for(ClientHandler clientHandler: clientHandlers){
//                clientHandler.notifyAll();
//            }
            pool.shutdown();
        } catch (IOException e) {
            System.err.println("There is problem in I/O");
        } catch (NullPointerException e){
            System.err.println("There is problem in pointing");
        } catch (RejectedExecutionException e){
            System.err.println("After shouting down the ExecutorService, you can not add another client");
        }
    }

    /**
     * Down server
     */
    public void downServer(){
        this.running = false;
    }

}
