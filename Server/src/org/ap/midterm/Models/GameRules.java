package org.ap.midterm.Models;

import org.ap.midterm.Models.Mafia.Mafia;

import java.util.ArrayList;

/**
 * @author Hamidreza Abooei
 */
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

    /**
     * apply game rules
     */
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
                // if the number of players become more than 10, it should be change a little bit
                ((Mafia)gameState.getPlayer("Mafia")).setKiller();
            }
        }
    }

    /**
     * add one event
     * @param sendUsername A username that send this message
     * @param message the message (event)
     */
    public void addEvent(String sendUsername , String message){
        boolean addOrNot = true;
        int usernameID = -1;
        if (gameState.getGameMode() == GameMode.NIGHT){
            usernameID = toInteger(sendUsername , message);
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
        }

        if (gameState.getGameMode() == GameMode.DAY){
            usernameID = toInteger(sendUsername , message);
            ArrayList<String> choices = gameState.getAliveUsernames();
            message = choices.get(usernameID);
        }

        if (usernameID != -1) {
            eventSenders.add(sendUsername);
            events.add(message);
//            System.out.println("event Added user:" + sendUsername + " event " + message );
        }
    }


    /**
     * to handle exception
     * @param sendUsername username
     * @param message message to integer
     * @return integer of message
     */
    private int toInteger(String sendUsername , String message){
        int usernameID ;
        try{
            usernameID = Integer.parseInt(message);
            return usernameID ;
        }catch (NumberFormatException e){
            gameManager.sendMessageToClientHandler(sendUsername,"wrong input");
            gameManager.sendMessageToClientHandler(sendUsername,"read");
        }
        return -1;
    }
}
