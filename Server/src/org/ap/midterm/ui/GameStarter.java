package org.ap.midterm.ui;

public class GameStarter {
    private static int clientNumbers;
    public GameStarter(){
        clientNumbers = 0;
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
    }

}
