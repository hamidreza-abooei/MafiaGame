package org.ap.midterm.Models;


public class GameLoop {
    GameManager gameManager;
//    GameState gameState;
    public GameLoop(GameManager gameManager){
        this.gameManager = gameManager;
    }
    public synchronized void start(){
        firstNight();
        while(true){
            day();
            election();
            applyChanges();
            night();
        }
    }
    private void firstNight(){
        gameManager.startMafiaChatRoom();
    }
    private void day(){

    }
    private void election(){

    }
    private void night(){

    }
    public void applyChanges(){

    }


}
