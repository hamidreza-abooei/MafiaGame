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

    /**
     * constructor
     * @param gameState game state
     * @param gameManager game manager
     */
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
        checkEndOfGame();
        events.clear();
        eventSenders.clear();
    }

    /**
     * apply changes in night
     */
    private void nightApply(){
        String mafiaKillPlayer = "";
        String DoctorHealer = "";
        String hunterKill = "";
        String DrlecterHealer = "";
        int counter = 0;
        for(String eventSender:eventSenders){
            // kill Citizen by mafia
            if (eventSender.equalsIgnoreCase(gameState.getKillerUsername())){
                mafiaKillPlayer = events.get(counter);
            }else if(gameState.getUsernameRule(eventSender).equalsIgnoreCase("DrLecter")){
                DrlecterHealer = events.get(counter);
            }

            if (gameState.getUsernameRule(eventSender).equalsIgnoreCase("Doctor")){
                DoctorHealer = events.get(counter);
            }
            // kill by Hunter
            if (gameState.getUsernameRule(eventSender).equalsIgnoreCase("hunter")){
                if (gameState.getPlayer(events.get(counter)) instanceof Mafia){
                    hunterKill = events.get(counter);
                }else{
                    //Suicide
                    hunterKill = "Hunter";
                }
            }

            // detective
            if (gameState.getUsernameRule(eventSender).equalsIgnoreCase("Detective")){
                if (gameState.getUsernameRule(events.get(counter)).equalsIgnoreCase("Mafia") ||
                        gameState.getUsernameRule(events.get(counter)).equalsIgnoreCase("DrLecter")){
                    gameManager.DetectiveInquiryResult(true);
                }else{
                    gameManager.DetectiveInquiryResult(false);
                }
            }

            // die hard inquiry
            if(gameState.getUsernameRule(eventSender).equalsIgnoreCase("DieHard")){
                gameManager.dieHardInquiryResult(gameState.getUsernameRule(events.get(counter)));
            }

            // Psychologist silence rule
            if (gameState.getUsernameRule(eventSender).equalsIgnoreCase("Psychologist")){
                gameManager.silenceUsername(events.get(counter));
            }


            counter++;
        }

        // apply kills
        if (mafiaKillPlayer.length()>0){
            if (!mafiaKillPlayer.equalsIgnoreCase(DoctorHealer)) {
                gameState.killPlayer(mafiaKillPlayer);
            }
        }
        if (hunterKill.length()>0){
            if(!hunterKill.equalsIgnoreCase(DrlecterHealer)) {
                gameState.killPlayer(hunterKill);
            }
        }

    }

    /**
     * apply changes in day
     */
    private void electionApply(){
        int counter = 0;
        boolean veto = false;
        boolean president = false; // this is for vote and veto
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Integer> votes = new ArrayList<>();

        for (String eventSender : eventSenders){
            if (gameState.getUsernameRule(eventSender).equalsIgnoreCase("President")){
                if (!president){
                    president = true;
                }else{
                    if (!events.get(counter).equalsIgnoreCase("-1")){
                        veto = true;
                    }
                }
            }else {
                String voted = events.get(counter);
                int voteCounter = 0;
                boolean usernameExist = false;
                for (String username : usernames){
                    if (voted.equalsIgnoreCase(username)){
                        votes.set(voteCounter,votes.get(voteCounter)+1);
                        usernameExist = true;
                    }
                    voteCounter++;
                }
                if (!usernameExist){
                    usernames.add(voted);
                    votes.add(1);
                }
            }
            counter++;
        }

        counter = 0;
        int maxVote = 0;
        int number = -1;
        for (Integer vote : votes){
            if(vote>maxVote){
                maxVote = vote;
                number = counter;
            }
            counter++;
        }
        if (!veto){
            if(maxVote>1){
                gameState.killPlayer(usernames.get(number));
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
        int usernameID = -2;
        if (gameState.getGameMode() == GameMode.NIGHT){
            usernameID = toInteger(sendUsername , message);
            if (usernameID != -1) {
                ArrayList<String> choices = gameManager.getAliveCitizens();
                message = choices.get(usernameID);
                if (!sendUsername.equalsIgnoreCase(gameState.getKillerUsername())) {
                    // this part is for Dr.Lecter that can do two things in one night
                    boolean kill = true; // between kill and save
                    for (String eventSender : eventSenders) {
                        if (eventSender.equalsIgnoreCase(sendUsername)) {
                            kill = false;
                        }
                    }
                    if (kill) {
                        gameManager.sendMessageToKiller(sendUsername + " opinion is to kill " + message);
                        addOrNot = false;
                    }
                }
            }
        }

        if (gameState.getGameMode() == GameMode.DAY){
            usernameID = toInteger(sendUsername , message);
            ArrayList<String> choices = gameState.getAliveUsernames();
            message = choices.get(usernameID);
        }

        if (usernameID != -2 && addOrNot) {
            eventSenders.add(sendUsername);
            events.add(message);
//            System.out.println("event Added user:" + sendUsername + " event " + message );
        }
    }

    /**
     * check game over
     * @return game over or not
     */
    private void checkEndOfGame(){
        int numberOfAliveCitizens = gameState.getAliveCitizens().size();
        int numberOfAliveMafias = gameState.getMafiaUserNames().size();
        if (numberOfAliveMafias == 0){
            winner("Citizens");
        }
        if (numberOfAliveMafias >= numberOfAliveCitizens ){
            winner("Mafias");
        }
    }

    /**
     * broadcast winner and GameOVER
     * @param winner winner of the game
     */
    private void winner(String winner){
        gameManager.broadcastMessage("Winner is: " + winner);
        //end of game
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
