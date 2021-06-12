package org.ap.midterm.Models;

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
    public void addPlayers(ArrayList<Player> players){
        this.players = players;
    }

    /**
     * add new client handler (client)
     * @param username username of client
     * @param clientHandler client handler to keep
     */
    private void addUser(String username, ClientHandler clientHandler){
        usernames.add(username);
        clientHandlerHashMap.put(username,clientHandler);
    }

    /**
     * check username that it doesn't been registered yet
     * @param username new username to check
     * @param clientHandler client handler
     * @return is it registered or not
     */
    public boolean checkUsername(String username,ClientHandler clientHandler){
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
     * @param playerIndex the player index to kill
     */
    public void killPlayer(int playerIndex) {
        players.get(playerIndex).die();
    }

    /**
     *
     * @return get all player
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * get Mafias client handlers
     * @return ArrayList of Client handlers
     */
    public ArrayList<ClientHandler> getMafiaClientHandler(){
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
    public ArrayList<String> getMafiaUserNames(){
        ArrayList<String> mafiaUserNames = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) instanceof Mafia){
                if (players.get(i).isAlive()) {
                    mafiaUserNames.add(usernames.get(i));
                    System.out.println("mafia username:" + usernames.get(i) + "rule is: " + players.get(i).toString());
                }
            }
        }
        return mafiaUserNames;
    }

    /**
     * get Username and client handler Map
     * @return Hash map of username and ClientHandler
     */
    public HashMap<String, ClientHandler> getClientHandlerHashMap() {
        return clientHandlerHashMap;
    }
}
