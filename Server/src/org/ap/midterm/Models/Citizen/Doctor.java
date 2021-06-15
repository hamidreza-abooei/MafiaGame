package org.ap.midterm.Models.Citizen;
/**
 * @author Hamidreza Abooei
 */
public class Doctor extends Citizen{
    /**
     * Constructor
     */
    private boolean selfGuard = false;
    public Doctor(){
        super();
        super.setName("Doctor");
    }

    /**
     * Doctor can guard himSelf once
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
