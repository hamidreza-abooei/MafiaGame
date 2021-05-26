package org.ap.midterm.Models;

public abstract class Player{
    protected boolean alive = true;
    protected String name;

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
}
