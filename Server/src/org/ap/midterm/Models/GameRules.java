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
            nightApply();
        }

        // after election
        if (gameState.getGameMode()==GameMode.ELECTION){
            electionApply();
        }


        if (!gameState.getPlayer("GodFather").isAlive()){
            if (gameState.getPlayer("DrLecter").isAlive()){
                ((Mafia)gameState.getPlayer("DrLecter")).setKiller();
            }else{
                // if the number of players become more than 10, it should be change a little bit
                ((Mafia)gameState.getPlayer("Mafia")).setKiller();
            }
        }
        // clear events after apply it
        events.clear();
        eventSenders.clear();
    }

    /**
     * apply changes in night
     */
    private void nightApply(){
        String mafiaKillPlayer = "";
        int counter = 0;
        for(String eventSender:eventSenders){
            // kill Citizen
            if (gameState.getUsernameRule(eventSender).equalsIgnoreCase("Mafia") ||
                    gameState.getUsernameRule(eventSender).equalsIgnoreCase("GodFather") ||
                    gameState.getUsernameRule(eventSender).equalsIgnoreCase("DrLecter")){
                mafiaKillPlayer = events.get(counter);
            }else if (gameState.getUsernameRule(eventSender).equalsIgnoreCase("Doctor")){
                if(mafiaKillPlayer.equalsIgnoreCase(events.get(counter))){
                    mafiaKillPlayer = "";
                }
            }
            if (mafiaKillPlayer.length()>0){
                gameState.killPlayer(mafiaKillPlayer);
            }

            // kill by Hunter
//            if ()
            counter++;
        }
    }

    /**
     * apply changes in day
     */
    private void electionApply(){

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
                    addOrNot= false;
                }
            }
        }

        if (gameState.getGameMode() == GameMode.DAY){
            usernameID = toInteger(sendUsername , message);
            ArrayList<String> choices = gameState.getAliveUsernames();
            message = choices.get(usernameID);
        }

        if (usernameID != -1 && addOrNot) {
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
