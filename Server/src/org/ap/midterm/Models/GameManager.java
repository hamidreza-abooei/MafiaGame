package org.ap.midterm.Models;

import org.ap.midterm.dependencies.GameInitiator;

public class GameManager {
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
        gameLoop.play();
    }
    public boolean checkUsername(String username){
//        System.out.println("check username Game Manager");
        return gameState.checkUsername(username);
    }


}
