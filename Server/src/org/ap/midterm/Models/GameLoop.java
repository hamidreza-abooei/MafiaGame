package org.ap.midterm.Models;


public class GameLoop {
    private GameMode gameMode = GameMode.NIGHT;
    public void play(){
        while(true){
            if (gameMode == GameMode.NIGHT){



                gameMode = GameMode.DAY;
            }else if (gameMode == GameMode.DAY){

                gameMode = GameMode.ELECTION;
            }else if(gameMode == GameMode.ELECTION){

            }


        }
    }

}
