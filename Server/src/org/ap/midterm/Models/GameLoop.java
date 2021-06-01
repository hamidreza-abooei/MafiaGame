package org.ap.midterm.Models;


public class GameLoop {
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
