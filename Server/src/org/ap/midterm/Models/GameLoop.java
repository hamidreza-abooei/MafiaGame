package org.ap.midterm.Models;

/**
 * @author Hamidreza Abooei
 */
public class GameLoop {
    GameManager gameManager;
//    GameState gameState;

    /**
     * constructor
     * @param gameManager game manager to keep in touch with other part of game
     */
    public GameLoop(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * start the game loop
     */
    public synchronized void start(){
        firstNight();
        while(true){
            day();
            election();
            applyChanges();
            night();
            applyChanges();
        }
    }

    /**
     * things that should be done in the first night
     */
    private void firstNight(){
        gameManager.startMafiaChatRoom();
    }

    /**
     * things that is done in the day
     */
    private void day(){

    }

    /**
     * things that is done in the election
     */
    private void election(){

    }

    /**
     * things that is been done in the night
     */
    private void night(){

    }

    /**
     * apply changes after election and night
     */
    public void applyChanges(){

    }


}
