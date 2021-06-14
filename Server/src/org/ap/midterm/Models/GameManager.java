package org.ap.midterm.Models;

import org.ap.midterm.dependencies.GameInitiator;
import org.ap.midterm.ui.Chat.ChatServer;
import org.ap.midterm.ui.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hamidreza Abooei
 */
public class GameManager implements Runnable {
    // Fields
    private GameState gameState;
    private GameLoop gameLoop;
    private ChatServer chatServer;
    private ChatServer mafiaChatServer;
    private int playerCount;
    private int chatServerPort;
    private int mafiaChatServerPort;
    /**
     * Constructor and initiator
     * @param playerCount the number of players
     */
    public GameManager(int playerCount){
        gameState = new GameState();
        gameLoop = new GameLoop(this);
        this.playerCount = playerCount;
        chatServerPort = 2585;
        mafiaChatServerPort = 8654;
        chatServer = new ChatServer(chatServerPort , false);
        mafiaChatServer = new ChatServer(mafiaChatServerPort , true);
    }

    /**
     * get the number of players
     * @return the number of players
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * play the Mafia Game
     */
    public synchronized void play(){
        GameInitiator gameInitiator = new GameInitiator(playerCount);
        gameState.addPlayers(gameInitiator.initiatePlayers());
        HashMap<String , ClientHandler> clientHandlers = gameState.getClientHandlerHashMap();
        ArrayList<Player> players = gameState.getPlayers();
        ArrayList<String> usernames = gameState.getUsernames();
        int counter = 0;
//        for (int i = 0; i < clientHandlers.size(); i++) {
            for(String username: usernames){
                for(Map.Entry<String,ClientHandler> clientHandlerEntry: clientHandlers.entrySet()){
                    if (clientHandlerEntry.getKey().equalsIgnoreCase(username)){
                        clientHandlerEntry.getValue().startWriting("clientRule");
                        clientHandlerEntry.getValue().startWriting(players.get(counter).toString());
                    }
                }
                counter++;
            }
//        }
        {
//            client.startWriting("clientRule");
//            client.startWriting(players.get(counter).toString());
//            System.out.println("Manager before loop: " + players.get(counter).toString());
//            counter ++;
        }
        gameLoop.start();
    }

    /**
     *
     * @param username the username to check its availability
     * @param clientHandler the client handler of that Player
     * @return available or not
     */
    public boolean checkUsername(String username, ClientHandler clientHandler){
        return gameState.checkUsername(username , clientHandler);
    }

    /**
     * read from client
     * @param string message
     */
    public void readFromClient(String string){

    }

    /**
     * start mafia chat room
     */
    public synchronized void startMafiaChatRoom() {
        ArrayList<ClientHandler> mafias = gameState.getMafiaClientHandler();
//        mafiaChatServer.addClientHandlers(mafias);
//        try {
            Thread mafiaChat = new Thread(mafiaChatServer);
            mafiaChat.start();
        for (ClientHandler mafia: mafias) {
            System.out.println("mafia: " + mafia.getUsername() );
            mafia.startWriting("startChat");
            mafia.startWriting(mafia.getUsername());
            System.out.println("mafiachat start: " + mafia.getUsername());
            mafia.startWriting(String.valueOf(mafiaChatServerPort));

        }
//            Thread.sleep(60000);
//            mafiaChatServer.closeServer();
//        } catch (InterruptedException e) {
//            System.err.println("Interrupted");
//        }

    }



    /**
     * run the thread
     */
    @Override
    public void run() {
        play();
    }
}
