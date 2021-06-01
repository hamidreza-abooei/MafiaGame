package org.ap.midterm.Models;

import org.ap.midterm.dependencies.GameInitiator;
import org.ap.midterm.ui.ChatServer;
import org.ap.midterm.ui.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager implements Runnable {
    GameState gameState;
    GameLoop gameLoop;
    ChatServer chatServer;
    int playerCount;
    public GameManager(int playerCount){
        gameState = new GameState();
        gameLoop = new GameLoop(this);
        this.playerCount = playerCount;
        chatServer = new ChatServer();
    }

    public int getPlayerCount() {
        return playerCount;
    }

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
    public boolean checkUsername(String username, ClientHandler clientHandler){
        return gameState.checkUsername(username , clientHandler);
    }
    public void readFromClient(String string){

    }
    public void startMafiaChatRoom(){
        ArrayList<ClientHandler> mafias = gameState.getMafiaClientHandler();

    }


    @Override
    public void run() {
        play();
    }
}
