package Domain;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Game {
    int counter = 4;
    String game_id;
    String date;
    int hour;
    String home_team_ID;
    String external_team_ID;
    String result;
    String main_referee_ID;
    String secondary_referee_ID1;
    String secondary_referee_ID2;
    String courtID;
    String leagueID;

    public Game(String home_team, String external_team) {
        this.home_team_ID = home_team;
        this.external_team_ID = external_team;
        this.game_id = "GAME"+counter++;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getHome_team_ID() {
        return home_team_ID;
    }

    public void setHome_team_ID(String home_team_ID) {
        this.home_team_ID = home_team_ID;
    }

    public String getExternal_team_ID() {
        return external_team_ID;
    }

    public void setExternal_team_ID(String external_team_ID) {
        this.external_team_ID = external_team_ID;
    }

    public String getMain_referee_ID() {
        return main_referee_ID;
    }

    public void setMain_referee_ID(String main_referee_ID) {
        this.main_referee_ID = main_referee_ID;
    }

    public String getSecondary_referee_ID1() {
        return secondary_referee_ID1;
    }

    public void setSecondary_referee_ID1(String secondary_referee_ID1) {
        this.secondary_referee_ID1 = secondary_referee_ID1;
    }

    public String getSecondary_referee_ID2() {
        return secondary_referee_ID2;
    }

    public void setSecondary_referee_ID2(String secondary_referee_ID2) {
        this.secondary_referee_ID2 = secondary_referee_ID2;
    }

    public String getCourtID() {
        return courtID;
    }

    public void setCourtID(String courtID) {
        this.courtID = courtID;
    }

    public String getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(String leagueID) {
        this.leagueID = leagueID;
    }

    public void game_placement(String date, int hour , String court, String leagueID,String PolicyID,String home_court_id,String external_court_id){
        this.date = date;
        this.hour = hour;
        this.courtID = court;
        this.leagueID = leagueID;

        //placement game to policy
        //this.leagueID.getGame_policy().add_game_to_league(this.league, this);

        //placement game to policy - new

        switch(PolicyID){
            case "POLICY1":
                GamePlacementPolicy1.add_game_to_league(this,home_court_id,external_court_id);
            case "POLICY2":
                GamePlacementPolicy2.add_game_to_league(this,home_court_id,external_court_id);

        }


    }
}
