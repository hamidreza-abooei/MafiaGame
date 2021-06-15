package org.ap.midterm.Models;
/**
 * @author Hamidreza Abooei
 */
public abstract class Player{
    private boolean alive = true;
    private String name;

    /**
     * getter
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter
     * @param userName set name
     */
    public void setName(String userName) {
        this.name = userName;
    }

    /**
     * die player
     */
    public void die(){
        alive = false;
    }

    /**
     * get live status
     * @return alive
     */
    public boolean isAlive(){
        return alive;
    }

//    /**
//     * night job of player
//     */
//    public void nightJob(){
//
//    }
//
//    /**
//     * day job of player
//     */
//    public void dayJob(){
//
//    }
//
//    /**
//     * election job of player
//     */
//    public void electionJob(){
//
//    }

    /**
     *
     * @return to string (name of player)
     */
    @Override
    public String toString(){
        return name;
    }

}
