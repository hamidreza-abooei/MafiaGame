package org.ap.midterm.Models;

import org.ap.midterm.Models.Citizen.Citizen;
import org.ap.midterm.Models.Mafia.Mafia;
import org.ap.midterm.ui.ClientHandler;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Hamidreza Abooei
 */
public class GameState {
    // Fields
    private ArrayList<Player> players;
    private ArrayList<String> usernames;
    private HashMap<String , ClientHandler> clientHandlerHashMap;
    private GameMode gameMode;

    /**
     * Constructor
     */
    public GameState(){
        usernames = new ArrayList<>();
        clientHandlerHashMap = new HashMap<>();
    }

    /**
     * add all player
     * @param players add players from GameInitiator
     */
    public synchronized void addPlayers(ArrayList<Player> players){
        this.players = players;
    }

    /**
     * add new client handler (client)
     * @param username username of client
     * @param clientHandler client handler to keep
     */
    private synchronized void addUser(String username, ClientHandler clientHandler){
        usernames.add(username);
        clientHandlerHashMap.put(username,clientHandler);
    }

    /**
     * check username that it doesn't been registered yet
     * @param username new username to check
     * @param clientHandler client handler
     * @return is it registered or not
     */
    public synchronized boolean checkUsername(String username,ClientHandler clientHandler){
        if (username.length()<2)
            return false;
        for (String anyUsername: clientHandlerHashMap.keySet()) {
            if(anyUsername.equalsIgnoreCase(username)){
                return false;
            }
        }
        addUser(username,clientHandler);
        return true;
    }

    /**
     * kill player
     * @param usernameToKill the player to kill
     */
    public synchronized void killPlayer(String usernameToKill) {
        int counter = 0;
        for (String username:usernames){
            if (username.equalsIgnoreCase(usernameToKill))
                break;
            counter++;
        }
        players.get(counter).die();
    }

    /**
     *
     * @return get all player
     */
    public synchronized ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * get Mafias client handlers
     * @return ArrayList of Client handlers
     */
    public synchronized ArrayList<ClientHandler> getMafiaClientHandler(){
        ArrayList<ClientHandler> mafiaClientHandlers = new ArrayList<>();
        ArrayList<String> mafiaUserNames =getMafiaUserNames();
        for (String username : mafiaUserNames) {
            mafiaClientHandlers.add(clientHandlerHashMap.get(username));
        }
        return mafiaClientHandlers;
    }

    /**
     * get mafias client username
     * @return ArrayList of Usernames
     */
    public synchronized ArrayList<String> getMafiaUserNames(){
        ArrayList<String> mafiaUserNames = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) instanceof Mafia){
                if (players.get(i).isAlive()) {
                    mafiaUserNames.add(usernames.get(i));
                }
            }
        }
        return mafiaUserNames;
    }

    /**
     *
     * @param usernameToGetRule username that we want its rule
     * @return rule of that username
     */
    public synchronized String getUsernameRule(String usernameToGetRule){
        int counter = 0;
        for(String username:usernames){
            if (username.equalsIgnoreCase(usernameToGetRule))
                break;
            counter++;
        }
        return players.get(counter).toString();
    }

    /**
     * get Username and client handler Map
     * @return Hash map of username and ClientHandler
     */
    public synchronized HashMap<String, ClientHandler> getClientHandlerHashMap() {
        return clientHandlerHashMap;
    }
    public ArrayList<String> getUsernames(){
        return usernames;
    }

    /**
     * get gameMode
     * @return game mode
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * set game mode
     * @param gameMode game mode to set
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * get all client handlers
     * @return arraylist of all alive client handlers
     */
    public ArrayList<ClientHandler> getAllClientHandlers(){
        ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
        int counter = 0;
        for (String username : usernames) {
            if (players.get(counter).isAlive())
                clientHandlers.add(clientHandlerHashMap.get(username));
            counter++;
        }
        return clientHandlers;
    }

    /**
     * get alive citizens
     * @return alive citizens
     */
    public ArrayList<String> getAliveCitizens(){
        int counter = 0;
        ArrayList<String> aliveCitizens = new ArrayList<>();
        for(String username:usernames){
            if (players.get(counter) instanceof Citizen)
                if (players.get(counter).isAlive())
                    aliveCitizens.add(username);
            counter++;
        }
        return aliveCitizens;
    }

    /**
     * get killer client handler
     * @return killer client handler
     */
    public ClientHandler getKillerClientHandler(){
        return clientHandlerHashMap.get(getKillerUsername());
    }

    /**
     * killer username
     * @return username
     */
    public String getKillerUsername (){
        int counter = 0;
        for(Player player: players){
            if(player instanceof Mafia)
                if(((Mafia) player).isKiller() )
                    break;
            counter++;
        }
        return usernames.get(counter);
    }

    /**
     * get client handler of username
     * @param usernameOfClient client
     * @return client handler
     */
    public ClientHandler getClientHandler(String usernameOfClient){
        return clientHandlerHashMap.get(usernameOfClient);
    }

    /**
     * get player of username or player name
     * @param usernameOrPlayerName username or player name
     * @return player of entered input
     */
    public Player getPlayer(String usernameOrPlayerName){
        for (Player player:players){
            if (usernameOrPlayerName.equalsIgnoreCase(player.toString())){
                return player;
            }
        }
        int counter = 0;
        for (String username:usernames){
            if (username.equalsIgnoreCase(usernameOrPlayerName)){
                return players.get(counter);
            }
            counter++;
        }
        return null;
    }

    /**
     *
     * @return alive usernames
     */
    public ArrayList<String> getAliveUsernames(){
        int counter = 0;
        ArrayList<String> alive = new ArrayList<>();
        for(String username:usernames){
            if (players.get(counter).isAlive())
                alive.add(username);
            counter++;
        }
        return alive;
    }

    /**
     * get Client Handler Of Player
     * @param playerName player name
     * @return client handler of that player name
     */
    public ClientHandler getClientHandlerOfPlayer(String playerName){
        int counter = 0;
        for (Player player:players){
            if (player.toString().equalsIgnoreCase(playerName))
                break;
            counter ++;
        }
        return clientHandlerHashMap.get(usernames.get(counter));
    }

}
