package org.ap.midterm.Models;

import org.ap.midterm.Models.Citizen.DieHard;
import org.ap.midterm.Models.Mafia.Mafia;
import org.ap.midterm.dependencies.GameInitiator;
import org.ap.midterm.ui.Chat.ChatServer;
import org.ap.midterm.ui.ClientHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hamidreza Abooei
 */
public class GameManager implements Runnable {
    // Fields
    private GameState gameState;
    private GameLoop gameLoop;
    private ChatServer chatServer;
    private ChatServer mafiaChatServer;
    private int playerCount;
    private int chatServerPort;
    private int mafiaChatServerPort;
    private GameRules gameRules;
    /**
     * Constructor and initiator
     * @param playerCount the number of players
     */
    public GameManager(int playerCount){
        gameState = new GameState();
        gameLoop = new GameLoop(this);
        this.playerCount = playerCount;
        chatServerPort = 2585;
        mafiaChatServerPort = 8654;
        chatServer = new ChatServer(chatServerPort , false , this , 60 );
        mafiaChatServer = new ChatServer(mafiaChatServerPort , true , this,30 );
        gameRules = new GameRules(gameState , this);
    }

    /**
     * get the number of players
     * @return the number of players
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * play the Mafia Game
     */
    public synchronized void play(){
        GameInitiator gameInitiator = new GameInitiator(playerCount);
        gameState.addPlayers(gameInitiator.initiatePlayers());
        HashMap<String , ClientHandler> clientHandlers = gameState.getClientHandlerHashMap();
        ArrayList<Player> players = gameState.getPlayers();
        ArrayList<String> usernames = gameState.getUsernames();
        int counter = 0;
        for(String username: usernames){
            for(Map.Entry<String,ClientHandler> clientHandlerEntry: clientHandlers.entrySet()){
                if (clientHandlerEntry.getKey().equalsIgnoreCase(username)){
                    clientHandlerEntry.getValue().startWriting("clientRule");
                    clientHandlerEntry.getValue().startWriting(players.get(counter).toString());
                }
            }
            counter++;
        }
        gameLoop.start();
    }

    /**
     * send message to killer
     * @param message message to send
     */
    public void sendMessageToKiller(String message){
        gameState.getKillerClientHandler().startWriting(message);
    }

    /**
     *
     * @param username the username to check its availability
     * @param clientHandler the client handler of that Player
     * @return available or not
     */
    public boolean checkUsername(String username, ClientHandler clientHandler){
        return gameState.checkUsername(username , clientHandler);
    }

    /**
     * read from client
     * @param string message
     */
    public void readFromClient(String sendUsername , String string){
        gameRules.addEvent(sendUsername , string);
    }

    /**
     * start mafia chat room
     */
    public synchronized void startMafiaChatRoom() {
        ArrayList<ClientHandler> mafias = gameState.getMafiaClientHandler();
        Thread mafiaChat = new Thread(mafiaChatServer);
        mafiaChat.start();
        for (ClientHandler mafia: mafias) {
            mafia.startWriting("startChat");
            mafia.startWriting(mafia.getUsername());
            mafia.startWriting(String.valueOf(mafiaChatServerPort));
        }
    }

    /**
     * start Public chat room
     */
    public synchronized void startPublicChatRoom(String silenceUsername) {
        chatServer.setSilenceUsername(silenceUsername);
        ArrayList<ClientHandler> players = gameState.getAllClientHandlers();
        Thread publicChat = new Thread(chatServer);
        publicChat.start();
        for (ClientHandler player: players) {
            player.startWriting("startChat");
            player.startWriting(player.getUsername());
            player.startWriting(String.valueOf(chatServerPort));
        }
    }

    /**
     * mafia broadcast
     * @param message
     */
    public synchronized void mafiaBroadcastMessage(String message){
        ArrayList<ClientHandler> mafias = gameState.getMafiaClientHandler();
        for (ClientHandler mafia: mafias) {
//            System.out.println("GameManager: mafia broadcast message" + mafia.toString() + message);
            mafia.startWriting(message);
        }
    }

    /**
     * apply changes and Game Rules
     */
    public void applyChanges(){
        gameRules.applyGameRules();
    }

    /**
     * broadcast message
     * @param message to send to all clients
     */
    public synchronized void broadcastMessage(String message){
        ArrayList<ClientHandler> allClientHandlers = gameState.getAllClientHandlers();
        for (ClientHandler clientHandler: allClientHandlers) {
            clientHandler.startWriting(message);
        }

    }

    /**
     * resmue game loop (notify wait in game loop)
     */
    public void resumeGameLoop(){
        gameLoop.resume();
    }

    /**
     * run the thread
     */
    @Override
    public void run() {
        play();
    }

    /**
     * set game mode
     * @param gameMode game mode
     */
    public void setGameMode(GameMode gameMode){
        gameState.setGameMode(gameMode);
    }

    /**
     * get alive citizens for mafias
     * @return Array list of usernames
     */
    public ArrayList<String> getAliveCitizens(){
        return gameState.getAliveCitizens();
    }

    /**
     *
     * @param usernameToSend
     * @param messageToSend
     */
    public void sendMessageToClientHandler(String usernameToSend , String messageToSend){
        gameState.getClientHandler(usernameToSend).startWriting(messageToSend);
    }

    /**
     * send rules to mafias in order to introduction
     */
    public void mafiaIntroduction (){
        ArrayList<String> userNames = gameState.getMafiaUserNames();
        for(String username: userNames){
            mafiaBroadcastMessage(username + " is: " + gameState.getUsernameRule(username));
        }
    }

    /**
     * vote
     */
    public void vote(){
        broadcastMessage("Vote");
        ArrayList<String> usernames = gameState.getAliveUsernames();
        int counter = 0;
        for (String username: usernames){
            broadcastMessage(counter + "- " + username);
            counter++;
        }
        broadcastMessage("read");
    }

    /**
     * veto voting by president
     */
    public void veto(){
        ClientHandler presidentClientHandler = gameState.getClientHandler("President");
        presidentClientHandler.startWriting("Do you want to veto this vote? press '-1' for reject");
        presidentClientHandler.startWriting("read");
    }

    /**
     * Detective can Inquiry Mafia
     */
    public void DetectiveInquiry(){
        ClientHandler detectiveClientHandler = gameState.getClientHandlerOfPlayer("Detective");
        ArrayList<String> usernames = gameState.getAliveUsernames();
        int counter = 0;
        for(String username:usernames) {
            detectiveClientHandler.startWriting(counter + "- " + username);
            counter++;
        }
        detectiveClientHandler.startWriting("read");
    }

    /**
     * return result of inquiry to Detective
     * @param isMafia result from GameRule
     */
    public void DetectiveInquiryResult(boolean isMafia){
        String message;
        if(isMafia){
            message = "Player is Mafia.";
        }else{
            message = "Player is NOT Mafia.";
        }
        gameState.getClientHandlerOfPlayer("Detective").startWriting(message);

    }

    /**
     * Doctor can save
     */
    public void DoctorSave(){
        ClientHandler doctorClientHandler = gameState.getClientHandlerOfPlayer("Doctor");
        ArrayList<String> usernames = gameState.getAliveUsernames();
        int counter = 0;
        doctorClientHandler.startWriting("Select one of the bellow usernames to save:");
        for(String username:usernames) {
            doctorClientHandler.startWriting(counter + "- " + username);
            counter++;
        }
        doctorClientHandler.startWriting("Warning: you can only save your self once");
        doctorClientHandler.startWriting("read");
    }

    /**
     * Doctor Lecter can save Mafia
     */
    public void DoctorLecterSave(){
        ClientHandler doctorLecterClientHandler = gameState.getClientHandlerOfPlayer("DrLecter");
        ArrayList<String> mafiaUsernames = gameState.getMafiaUserNames();
        int counter = 0;
        doctorLecterClientHandler.startWriting("Select one of the bellow mafias to save:");
        for (String mafiaUsername: mafiaUsernames){
            doctorLecterClientHandler.startWriting(counter + "- " + mafiaUsername);
            counter++;
        }
        doctorLecterClientHandler.startWriting("Warning: you can only save your self once");
        doctorLecterClientHandler.startWriting("read");
    }

    /**
     * mafia kill request
     */
    public void mafiaKillRequest(){
        mafiaBroadcastMessage("Select User to kill.");
        ArrayList<String> usernames = getAliveCitizens();
        for (int i = 0; i < usernames.size(); i++) {
            mafiaBroadcastMessage(i + "- " + usernames.get(i));
        }
        mafiaBroadcastMessage("read");
    }

    /**
     * hunter shooting
     */
    public void hunterShoot(){
        ClientHandler hunterClientHandler = gameState.getClientHandlerOfPlayer("Hunter");
        ArrayList<String> mafiaUsernames = gameState.getAliveUsernames();
        int counter = 0;
        hunterClientHandler.startWriting("Select one of the bellow usernames to Shoot:");
        for (String mafiaUsername: mafiaUsernames){
            hunterClientHandler.startWriting(counter + "- " + mafiaUsername);
            counter++;
        }
        hunterClientHandler.startWriting("Warning: if you shoot Citizens, you will die.");
        hunterClientHandler.startWriting("if you dont want to kill, press -1");
        hunterClientHandler.startWriting("read");
    }

    /**
     * psychologist Silence Act
     */
    public void psychologistSilenceAct(){
        ClientHandler hunterClientHandler = gameState.getClientHandlerOfPlayer("Psychologist");
        ArrayList<String> usernames = gameState.getAliveUsernames();
        int counter = 0;
        hunterClientHandler.startWriting("Select one of the bellow usernames to Silence next Day:");
        for (String mafiaUsername: usernames){
            hunterClientHandler.startWriting(counter + "- " + mafiaUsername);
            counter++;
        }
        hunterClientHandler.startWriting("read");
    }

    /**
     * die hard inquiry
     */
    public void dieHardInquiry(){
        if (((DieHard)gameState.getPlayer("DieHard")).isInquiryAble()) {
            ClientHandler dieHardClientHandler = gameState.getClientHandlerOfPlayer("DieHard");
            ArrayList<String> deadUsernames = gameState.getDeadUsernames();
            int counter = 0;
            dieHardClientHandler.startWriting("Select one of the bellow usernames to inquiry or press -1:");
            for (String mafiaUsername : deadUsernames) {
                dieHardClientHandler.startWriting(counter + "- " + mafiaUsername);
                counter++;
            }
            dieHardClientHandler.startWriting("read");
        }
    }

    /**
     * return rule to client
     * @param rule inquired rule
     */
    public void dieHardInquiryResult(String rule){
        gameState.getClientHandlerOfPlayer("DieHard").startWriting("Player tha you had inquired was:" + rule);
    }

    /**
     * username to be silent
     * @param username to be silent next rond
     */
    public void silenceUsername(String username){
        gameLoop.setSilenceUsername(username);
    }

}
