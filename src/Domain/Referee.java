package Domain;

import java.util.ArrayList;

public class Referee extends SignedUser {

    String refNum;
    League league;
    String qualification;
    ArrayList<Game> games_list;

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public ArrayList<Game> getGames_list() {
        return games_list;
    }

    public void setGames_list(ArrayList<Game> games_list) {
        this.games_list = games_list;
    }

    public Referee(String userName, String password) {
        super(userName, password);

    }

    @Override
    public String toString() {
        return "Referee{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
