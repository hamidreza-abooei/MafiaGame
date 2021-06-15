package org.ap.midterm.Models.Citizen;
/**
 * @author Hamidreza Abooei
 */
public class DieHard extends Citizen{
    private boolean shield = true;
    private int inquiry = 0;
    /**
     * constructor
     */
    public DieHard(){
        super();
        super.setName("DieHard");
    }

    /**
     * shielding
     */
    @Override
    public void die(){
        if (shield){
            shield = false;
        }else{
            super.die();
        }
    }

    /**
     * inquiry
     */
    public void inquiryDied(){
        inquiry++;
    }

    /**
     *
     * @return inquiry accepted or not
     */
    public boolean isInquiryAble(){
        if (inquiry < 2){
            return true;
        }
        return false;
    }

}
