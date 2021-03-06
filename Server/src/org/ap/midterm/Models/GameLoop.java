package org.ap.midterm.Models;

/**
 * @author Hamidreza Abooei
 */
public class GameLoop {
    private GameManager gameManager;
    private LoopTimer loopTimer;
    private String silenceUsername;
//    GameState gameState;

    /**
     * constructor
     * @param gameManager game manager to keep in touch with other part of game
     */
    public GameLoop(GameManager gameManager){
        this.gameManager = gameManager;
        this.silenceUsername = null;

    }

    /**
     * start the game loop
     */
    public synchronized void start(){
        firstNight();
        while(true){
            day();
            applyChanges();
            night(true);
            applyChanges();
        }
    }

    /**
     * things that should be done in the first night
     */
    private synchronized void firstNight(){
        night(false);
    }

    /**
     * things that is done in the day and election
     */
    private void day(){
        try {
            gameManager.setGameMode(GameMode.DAY);
            gameManager.startPublicChatRoom(silenceUsername);
            silenceUsername = null;
//            startTimer();
            wait();
            gameManager.setGameMode(GameMode.ELECTION);
            gameManager.vote();
            startTimer(30);
            wait();
            gameManager.veto();
            startTimer(20);
            wait();

        } catch (InterruptedException e) {
            System.err.println("Interrupted");
        }
    }


    /**
     * things that is been done in the night
     */
    private void night(boolean mafiaChat){
        try {
            gameManager.setGameMode(GameMode.NIGHT);
            gameManager.mafiaIntroduction();
            gameManager.DetectiveInquiry();
            gameManager.DoctorSave();
            gameManager.hunterShoot();
            gameManager.psychologistSilenceAct();
            if(mafiaChat) {
                gameManager.dieHardInquiry();
                gameManager.startMafiaChatRoom();
                wait();
            }
            gameManager.mafiaKillRequest();
//            System.out.println("read sent");
//            startTimer(60);
//            System.out.println("timer started 60 seconds");
            gameManager.DoctorLecterSave();
            wait();

        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }

    /**
     * apply changes after election and night
     */
    public void applyChanges(){
        gameManager.applyChanges();
    }

    /**
     *
     * @param length length of timer
     */
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

    /**
     * set silence username
     * @param silenceUsername username to be silent
     */
    public void setSilenceUsername(String silenceUsername){
        this.silenceUsername = silenceUsername;
    }


}
