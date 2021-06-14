package org.ap.midterm.Models;

public class LoopTimer implements Runnable {
    // Fields
    private GameLoop gameLoop;
    private int length;

    public LoopTimer (GameLoop gameLoop , int length){
        this.gameLoop = gameLoop;
        this.length = length * 1000;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(length);
            gameLoop.resume();
            System.out.println("Timer Ended");
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted");
        }
    }
}
