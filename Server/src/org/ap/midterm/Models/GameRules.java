package org.ap.midterm.Models;

import org.ap.midterm.Models.Mafia.Mafia;

import java.util.ArrayList;

public class GameRules {
    GameState gameState;
    GameManager gameManager;
    ArrayList<String> eventSenders;
    ArrayList<String> events;
    public GameRules(GameState gameState , GameManager gameManager){
        this.gameState = gameState;
        this.gameManager = gameManager;
        this.events = new ArrayList<>();
        this.eventSenders = new ArrayList<>();
    }

    public void applyGameRules(){
        // after night
        if (gameState.getGameMode()==GameMode.NIGHT){

        }
//        if (gameState.getGameMode()==GameMode.DAY){
//
//        }
        // after election
        if (gameState.getGameMode()==GameMode.ELECTION){

        }


        if (!gameState.getPlayer("GodFather").isAlive()){
            if (gameState.getPlayer("DrLecter").isAlive()){
                ((Mafia)gameState.getPlayer("DrLecter")).setKiller();
            }else{
                ((Mafia)gameState.getPlayer("Mafia")).setKiller();
            }
        }
    }
    public void addEvent(String sendUsername , String message){
        boolean addOrNot = true;

        if (gameState.getGameMode()==GameMode.NIGHT){
            try {
                int usernameID = Integer.parseInt(message);
                ArrayList<String> choices = gameManager.getAliveCitizens();
                message = choices.get(usernameID);
                if (!sendUsername.equalsIgnoreCase(gameState.getKillerUsername())) {
                    // this part is for Dr.Lecter that can do two things in one night
                    boolean kill = true; // between kill and save
                    for(String eventSender:eventSenders){
                        if (eventSender.equalsIgnoreCase(sendUsername)){
                            kill = false;
                        }
                    }
                    if(kill){
                        gameManager.sendMessageToKiller(sendUsername + " opinion is to kill " + message);
                    }
                }
            }catch (NumberFormatException e){
                gameManager.sendMessageToClientHandler(sendUsername,"wrong input");
                gameManager.sendMessageToClientHandler(sendUsername,"read");
                addOrNot = false;
            }
        }
        if (addOrNot) {
            eventSenders.add(sendUsername);
            events.add(message);
//            System.out.println("event Added user:" + sendUsername + " event " + message );
        }
    }
}
