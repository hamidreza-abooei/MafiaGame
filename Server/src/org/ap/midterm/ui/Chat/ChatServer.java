package org.ap.midterm.ui.Chat;

import org.ap.midterm.Models.GameManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author Hamidreza Abooei
 */
public class ChatServer implements Runnable{
    // Fields
    private ArrayList<ChatClientHandler> chatClientHandlers;
    private int port;
    private boolean running;
    private boolean chatPrint;
    private GameManager gameManager;
    private int timerLength;
    private String silenceUsername;

    /**
     * constructor
     * @param port port of mafia chat
     * @param rulePrint print player rule or not
     * @param gameManager game manager
     * @param timerLength chat timer
     */
    public ChatServer(int port , boolean rulePrint ,GameManager gameManager, int timerLength){
        chatClientHandlers = new ArrayList<>();
        this.port = port;
        this.chatPrint = rulePrint;
        this.gameManager = gameManager;
        this.timerLength = timerLength;
    }

    /**
     * start server
     */
    private synchronized void startChat(){
        ChatTimer chatTimer = new ChatTimer(this , timerLength);
        Thread timerThread = new Thread(chatTimer);
        timerThread.start();
        try (ServerSocket chatServerSocket = new ServerSocket(port)) {
            ExecutorService pool = Executors.newCachedThreadPool();
                System.out.println("Chat Server with port : " + port + " Started \nWaiting for Client .....");
                int clientNumber = 1;
                while (running){
                    chatClientHandlers.add(new ChatClientHandler(chatServerSocket.accept() , this , chatPrint));
                    pool.execute(chatClientHandlers.get(clientNumber-1));
                    System.out.println("Chat Server connected to new Client [Client-" + clientNumber + "]");
                    clientNumber++;
                }
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
     * broadcast message to other clients in the chat room
     * @param message message to be broadcast
     */
    public void broadcast(String message){
        for (ChatClientHandler chatClient: chatClientHandlers){
            chatClient.sendMessage(message);
        }
    }

    /**
     * check silence
     * @param username username red
     * @param clientHandler client handler of that client
     */
    public synchronized void checkSilence( String username, ChatClientHandler clientHandler){
        if(username.equalsIgnoreCase(silenceUsername)){
            clientHandler.setSilence();
        }
    }

    /**
     * close server
     */
    public void closeServer(){
        broadcast("stopChatClients");
        System.out.println("stopChatClients");
        running = false;
        gameManager.resumeGameLoop();
    }

    /**
     * set silence username
     * @param silenceUsername username to silence
     */
    public void setSilenceUsername(String silenceUsername){
        this.silenceUsername = silenceUsername;
    }
    /**
     * run this thread
     */
    @Override
    public void run() {
        running = true;
        startChat();
    }
}
