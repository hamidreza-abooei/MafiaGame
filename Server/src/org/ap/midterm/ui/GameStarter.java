package org.ap.midterm.ui;

import org.ap.midterm.Models.GameManager;

public class GameStarter {
    private static int clientNumbers;
    private GameManager gameManager;
    private int playerCount;
    Thread gameStart;
    boolean start;
    public GameStarter(GameManager gameManager , int playerCount){
        clientNumbers = 0;
        this.gameManager = gameManager;
        this.playerCount = playerCount;
        gameStart = new Thread(gameManager);
        start = false;
    }
    public synchronized void addClient (){
        clientNumbers ++;
    }
    public synchronized void waitForJoiningAllMembers(){
//        System.out.println("" + clientNumbers + "  " + (playerCount-1) );
        while (clientNumbers < playerCount){
            System.out.println(clientNumbers);
            System.out.println(playerCount );
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Game has been started");
        notify();
//        gameManager.play();
        if (!start) {
            gameStart.start();
          start=true;
        }
    }

}
