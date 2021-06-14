package org.ap.midterm.ui.Chat;

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
//    private int port;
//    private ArrayList<ClientHandler> clientHandlers;
    private ArrayList<ChatClientHandler> chatClientHandlers;
    private int port;
    private boolean running;
    private boolean chatPrint;
//    private int chatNumber;


    /**
     * constructor
     * @param port port of mafia chat
     */
    public ChatServer(int port , boolean chatPrint){
        chatClientHandlers = new ArrayList<>();
        this.port = port;
        this.chatPrint = chatPrint;
    }

//    /**
//     * add a client to chat room
//     * @param clientHandlers client
//     */
//    public void addClientHandlers(ArrayList<ClientHandler> clientHandlers){
//        this.clientHandlers = clientHandlers;
//        this.chatNumber = clientHandlers.size();
//    }

    /**
     * start server
     */
    private synchronized void startChat(){
        Timer timer = new Timer(this , 60);
        Thread timerThread = new Thread(timer);
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
     */
    public void broadcast(String message){
        for (ChatClientHandler chatClient: chatClientHandlers){
            chatClient.sendMessage(message);
        }
    }

//    private void createChatClientHandler(){
//        for (ClientHandler clientHandler: clientHandlers) {
//            chatClientHandlers.add(new ChatClientHandler(clientHandler.getConnection()));
//        }
//    }

    /**
     * close server
     */
    public void closeServer(){
        System.out.println("end and close server");
        broadcast("stopChatClients");
        running = false;
//        notifyAll();
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
