package org.ap.midterm.Models;

import org.ap.midterm.dependencies.GameInitiator;
import org.ap.midterm.ui.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager implements Runnable {
    GameState gameState;
    GameLoop gameLoop;
    int playerCount;
    public GameManager(int playerCount){
        gameState = new GameState();
        gameLoop = new GameLoop();
        this.playerCount = playerCount;
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
        gameLoop.play();
    }
    public boolean checkUsername(String username, ClientHandler clientHandler){
        return gameState.checkUsername(username , clientHandler);
    }
    public void readFromClient(String string){

    }


    @Override
    public void run() {
        play();
    }
}
