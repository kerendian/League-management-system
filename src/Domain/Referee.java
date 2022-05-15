package Domain;

import java.util.ArrayList;

public class Referee extends SignedUser {

    String refereeID;
    String refNum;
    String leagueID;
    String qualification;

    int counter= 5;



    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(String league) {
        this.leagueID = league;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Referee(String userName, String password, String refNum) {
        super(userName, password);
        this.refNum = refNum;
        this.refereeID = "REF"+ counter++;
    }

    @Override
    public String toString() {
        return "Referee{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
