package org.ap.midterm.Models;

public class GameRules {
    GameState gameState;
    public GameRules(GameState gameState){
        this.gameState = gameState;
    }

    public void applyGameRules(){
        // after night
        if (gameState.getGameMode()==GameMode.NIGHT){

        }
//        if (gameState.getGameMode()==GameMode.DAY){
//
//        }
        // after election
        if (gameState.getGameMode()==GameMode.ELECTION){

        }
    }
}
