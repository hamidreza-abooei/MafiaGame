package org.ap.midterm.Models;

import java.util.ArrayList;

/**
 * @author Hamidreza Abooei
 */
public class GameLoop {
    GameManager gameManager;
    LoopTimer loopTimer;
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
    private synchronized void firstNight(){
        try {
            gameManager.setGameMode(GameMode.NIGHT);
            gameManager.mafiaIntroduction();
//            gameManager.startMafiaChatRoom();
//            wait();

            gameManager.mafiaBroadcastMessage("Select User to kill.");
            ArrayList<String> usernames = gameManager.getAliveCitizens();
            for (int i = 0; i < usernames.size(); i++) {
                gameManager.mafiaBroadcastMessage(i + "- " + usernames.get(i));
            }
//            System.out.println("send read");
            gameManager.mafiaBroadcastMessage("read");
//            System.out.println("read sent");
//            startTimer(60);
//            System.out.println("timer started 60 seconds");
            wait();
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }


    }

    /**
     * things that is done in the day
     */
    private void day(){
        gameManager.startPublicChatRoom();

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
        gameManager.applyChanges();
    }

    private void startTimer(int length){
        Thread timerThread = new Thread(new LoopTimer(this,length));
        timerThread.start();
    }

    /**
     * resume
     */
    public synchronized void resume(){
        notify();
    }


}
