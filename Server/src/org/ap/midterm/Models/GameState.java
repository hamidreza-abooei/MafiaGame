package org.ap.midterm.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    private ArrayList<Player> players;
    private ArrayList<String> usernames;

    public GameState(){
//        players = new ArrayList<>();
        usernames = new ArrayList<>();
    }
    public void addPlayers(ArrayList<Player> players){
        this.players = players;
    }
    private void addUser(String username){
        usernames.add(username);
    }
    public boolean checkUsername(String username){
        for (String anyUsername: usernames) {
            if(anyUsername.equalsIgnoreCase(username)){
                return false;
            }
        }
        addUser(username);
        return true;
    }

    public void diePlayer(int playerIndex) {
        players.get(playerIndex).die();
    }
}
