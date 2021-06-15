package org.ap.midterm.Models.Citizen;
/**
 * @author Hamidreza Abooei
 */
public class Doctor extends Citizen{
    private boolean selfGuard = false;
    /**
     * Constructor
     */
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
