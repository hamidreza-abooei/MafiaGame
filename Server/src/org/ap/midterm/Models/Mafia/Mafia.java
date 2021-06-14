package org.ap.midterm.Models.Mafia;

import org.ap.midterm.Models.Player;
/**
 * @author Hamidreza Abooei
 */
public class Mafia extends Player {
    protected boolean killer;
    protected String killOrder;
    /**
     * constructor
     */
    public Mafia(){
        super();
        super.setName("Mafia");
        killer = false;
    }

    public void setKiller(){
        this.killer = true;
    }
    /**
     * night job of Mafia
     */
    @Override
    public void nightJob(){

    }

    /**
     * set kill order
     * @param killOrder kill order
     */
    public void setKillOrder(String killOrder){
        this.killOrder = killOrder;
    }

    /**
     * get kill order
     * @return kill order
     */
    public String getKillOrder(){
        return this.killOrder;
    }
}
