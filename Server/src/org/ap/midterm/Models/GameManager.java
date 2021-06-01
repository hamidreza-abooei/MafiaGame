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
        gameLoop.start();
    }
    public boolean checkUsername(String username, ClientHandler clientHandler){
        return gameState.checkUsername(username , clientHandler);
    }
    public void readFromClient(String string){

    }
    public void startChatRoom(ChatMode chatMode){
        ArrayList<ClientHandler> mafias = gameState.getMafiaClientHandler();
        for (ClientHandler mafia: mafias) {
            mafia.startWriting("Mafia Chat Room Started...");
            
        }
    }


    @Override
    public void run() {
        play();
    }
}
