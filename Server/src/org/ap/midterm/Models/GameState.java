package org.ap.midterm.Models;

import org.ap.midterm.Models.Mafia.Mafia;
import org.ap.midterm.ui.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    private ArrayList<Player> players;
    private ArrayList<String> usernames;
    private HashMap<String , ClientHandler> clientHandlerHashMap;

    public GameState(){
        usernames = new ArrayList<>();
        clientHandlerHashMap = new HashMap<>();
    }
    public void addPlayers(ArrayList<Player> players){
        this.players = players;
    }
    private void addUser(String username, ClientHandler clientHandler){
        usernames.add(username);
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

    public ArrayList<ClientHandler> getMafiaClientHandler(){
        ArrayList<ClientHandler> mafiaClientHandlers = new ArrayList<>();
        ArrayList<String> mafiaUserNames =getMafiaUserNames();

        for (String username : mafiaUserNames) {
            mafiaClientHandlers.add(clientHandlerHashMap.get(username));
        }
        return mafiaClientHandlers;
    }

    public ArrayList<String> getMafiaUserNames(){
        ArrayList<String> mafiaUserNames = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) instanceof Mafia){
                mafiaUserNames.add(usernames.get(i));
            }
        }
        return mafiaUserNames;
    }

    public HashMap<String, ClientHandler> getClientHandlerHashMap() {
        return clientHandlerHashMap;
    }
}
