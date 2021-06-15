package org.ap.midterm.Models.Mafia;

/**
 * @author Hamidreza Abooei
 */
public class DrLecter extends Mafia {
    private boolean selfGuard = false;

    /**
     * Constructor
     */
    public DrLecter(){
        super();
        super.setName("DrLecter");
    }

    /**
     * DoctorLecter can guard himSelf once
     * @return guarded or not
     */
    public boolean isSelfGuard() {
        return selfGuard;
    }

    /**
     * selfGuard
     */
    public void selfGuard() {
        this.selfGuard = true;
    }
}
