package org.ap.midterm.ui;

import org.ap.midterm.Models.GameManager;

public class GameStarter {
    private static int clientNumbers;
    private GameManager gameManager;
    public GameStarter(GameManager gameManager){
        clientNumbers = 0;
        this.gameManager = gameManager;
    }
    public synchronized void addClient (){
        clientNumbers ++;
    }
    public synchronized void waitForJoiningAllMembers(){
        while (clientNumbers<3){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        gameManager.play();
    }

}
