package org.ap.midterm.Models;

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

    @Override
    public String toString(){
        return name;
    }
}
