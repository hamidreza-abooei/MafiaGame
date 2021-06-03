package org.ap.midterm.ui;

import org.ap.midterm.Models.GameManager;

/**
 * @author Hamidreza Abooei
 */
public class GameStarter {
    // Fields
    private static int clientNumbers;
    private GameManager gameManager;
    private int playerCount;
    Thread gameStart;
    boolean start;

    /**
     * Game starter
     * @param gameManager game manager
     * @param playerCount number of players
     */
    public GameStarter(GameManager gameManager , int playerCount){
        clientNumbers = 0;
        this.gameManager = gameManager;
        this.playerCount = playerCount;
        gameStart = new Thread(gameManager);
        start = false;
    }

    /**
     * add client
     */
    public synchronized void addClient (){
        clientNumbers ++;
    }

    /**
     * wait untill all (10) players join
     */
    public synchronized void waitForJoiningAllMembers(){
        while (clientNumbers < playerCount){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        if (!start) {
            gameStart.start();
          start=true;
        }
    }

}
