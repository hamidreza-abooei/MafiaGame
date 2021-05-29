package org.ap.midterm.Models;

import org.ap.midterm.ui.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    private ArrayList<Player> players;
//    private ArrayList<String> usernames;
    private HashMap<String , ClientHandler> clientHandlerHashMap;

    public GameState(){
//        usernames = new ArrayList<>();
        clientHandlerHashMap = new HashMap<>();
    }
    public void addPlayers(ArrayList<Player> players){
        this.players = players;
    }
    private void addUser(String username, ClientHandler clientHandler){
//        usernames.add(username);
        clientHandlerHashMap.put(username,clientHandler);
    }

    public boolean checkUsername(String username,ClientHandler clientHandler){
        for (String anyUsername: clientHandlerHashMap.keySet()) {
            if(anyUsername.equalsIgnoreCase(username)){
                return false;
            }
        }
        addUser(username,clientHandler);
        return true;
    }

    public void killPlayer(int playerIndex) {
        players.get(playerIndex).die();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public HashMap<String, ClientHandler> getClientHandlerHashMap() {
        return clientHandlerHashMap;
    }
}
