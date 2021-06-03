package org.ap.midterm.Models;

import org.ap.midterm.dependencies.GameInitiator;
import org.ap.midterm.ui.ChatServer;
import org.ap.midterm.ui.ClientHandler;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Hamidreza Abooei
 */
public class GameManager implements Runnable {
    // Fields
    GameState gameState;
    GameLoop gameLoop;
    ChatServer chatServer;
    int playerCount;

    /**
     * Constructor
     * @param playerCount the number of players
     */
    public GameManager(int playerCount){
        gameState = new GameState();
        gameLoop = new GameLoop(this);
        this.playerCount = playerCount;
        chatServer = new ChatServer();
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
    public void play(){
        GameInitiator gameInitiator = new GameInitiator(playerCount);
        gameState.addPlayers(gameInitiator.initiatePlayers());
        HashMap<String , ClientHandler> clientHandlers = gameState.getClientHandlerHashMap();
        ArrayList<Player> players = gameState.getPlayers();
        int counter = 0;
        for (ClientHandler client: clientHandlers.values()) {
            client.startWriting(players.get(counter).toString());
            counter ++;
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
    public void startMafiaChatRoom(){
        ArrayList<ClientHandler> mafias = gameState.getMafiaClientHandler();

    }

    /**
     * run the thread
     */
    @Override
    public void run() {
        play();
    }
}
