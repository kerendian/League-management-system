package Domain;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Game {
    //check commitxgxg
    int counter = 1;
    String game_id;
    String date;
    int hour;
    Team home_team;
    Team external_team;
    String result;
    Referee main_referee;
    ArrayList<Referee> secondary_referees;
    Court court;
    League league;

    public Game(Team home_team, Team external_team) {
        this.home_team = home_team;
        this.external_team = external_team;
        this.game_id = "GM"+counter++;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Team getHome_team() {
        return home_team;
    }

    public void setHome_team(Team home_team) {
        this.home_team = home_team;
    }

    public Team getExternal_team() {
        return external_team;
    }

    public void setExternal_team(Team external_team) {
        this.external_team = external_team;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void game_placement(String date, int hour , Court court, League league){
        this.date = date;
        this.hour = hour;
        this.court = court;
        this.league = league;

        //placement game to policy
        this.league.getGame_policy().add_game_to_league(this.league, this);

    }
}
