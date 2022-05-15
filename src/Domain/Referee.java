package Domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Referee extends SignedUser {

    String refereeID;
    String refNum;
    String leagueID;
    String qualification;

    int counter= 5;


    public String getRefereeID() {
        return refereeID;
    }

    public void setRefereeID(String refereeID) {
        this.refereeID = refereeID;
    }

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


    public HashMap<String,String> get_referee_details(){
        HashMap<String,String> referee_details = new HashMap<>();
        referee_details.put("refereeID",this.refereeID);
        referee_details.put("refNum",this.refNum);
        referee_details.put("leagueID",this.leagueID);
        referee_details.put("qualification",this.qualification);
        referee_details.put("username",this.userName);
        referee_details.put("password",this.password);
        return referee_details;
    }


    @Override
    public String toString() {
        return "Referee{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
