package org.ap.midterm.dependencies;

import org.ap.midterm.Models.Citizen.*;
import org.ap.midterm.Models.Mafia.DrLecter;
import org.ap.midterm.Models.Mafia.GodFather;
import org.ap.midterm.Models.Mafia.Mafia;
import org.ap.midterm.Models.Player;

import java.util.ArrayList;
import java.util.Collections;
/**
 * @author Hamidreza Abooei
 */
public class GameInitiator {
    // Fields
    private int playerCount ;
    ArrayList<Player> players;

    /**
     * Constructor
     * @param playerCount the number of players
     */
    public GameInitiator(int playerCount ){
        this.playerCount = playerCount;
        players = new ArrayList<>();
    }

    /**
     * initiate players
     * @return arraylist of players
     */
    public ArrayList<Player> initiatePlayers(){
        createPlayers();
        shuffle();
        return players;
    }

    /**
     * create players
     */
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

    /**
     * shuffle players
     */
    private void shuffle(){
        Collections.shuffle(players);
    }
}
