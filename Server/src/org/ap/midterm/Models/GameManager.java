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
        GameInitiator gameInitiator = new GameInitiator();
        gameState.addPlayers(gameInitiator.initiatePlayers(playerCount));
        gameLoop.play();
    }
    public boolean checkUsername(String username){
        return gameState.checkUsername(username);
    }


}
