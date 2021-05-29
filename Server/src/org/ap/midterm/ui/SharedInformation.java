package org.ap.midterm.ui;

public class SharedInformation {
    private static int clientNumbers;
    public SharedInformation(){
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
