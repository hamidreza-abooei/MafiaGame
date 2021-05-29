package org.ap.midterm.dependencies;

import org.ap.midterm.Models.Citizen.*;
import org.ap.midterm.Models.Mafia.DrLecter;
import org.ap.midterm.Models.Mafia.GodFather;
import org.ap.midterm.Models.Mafia.Mafia;
import org.ap.midterm.Models.Player;

import java.util.ArrayList;
import java.util.Collections;

public class GameInitiator {
    private int playerCount ;
    ArrayList<Player> players;
    public GameInitiator(int playerCount ){
        this.playerCount = playerCount;
        players = new ArrayList<>();
    }

    public ArrayList<Player> initiatePlayers(){
        createPlayers();
        shuffle();
        return players;
    }

    private void createPlayers(){
        players.add(new GodFather());
        players.add(new DrLecter());
        players.add(new Detective());
        players.add(new Hunter());
        players.add(new DieHard());
        players.add(new President());
        players.add(new Doctor());
        players.add(new Psychologist());
        players.add(new Citizen());
        players.add(new Mafia());
        for (int i = 10; i < playerCount; i++) {
            if(playerCount%4 == 1){
                players.add(new Mafia());
            }else{
                players.add(new Citizen());
            }
        }

    }
    private void shuffle(){
        Collections.shuffle(players);
    }
}
