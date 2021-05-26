package org.ap.midterm.Models;

import java.util.ArrayList;

public class GameState {
    ArrayList<Player> players;
    public GameState(){
//        players = new ArrayList<>();
    }
    public void addPlayers(ArrayList<Player> players){
        this.players = players;
    }
    public void diePlayer(int playerIndex) {
        players.get(playerIndex).die();
    }
}
