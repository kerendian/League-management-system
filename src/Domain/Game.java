package Domain;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    int counter = 1;
    String game_id;
    Date date;
    int hour;
    Team home_team;
    Team external_team;
    String result;
    Referee main_referee;
    ArrayList<Referee> secondary_referees;

    public Game(Date date, int hour, Team home_team, Team external_team) {
        this.date = date;
        this.hour = hour;
        this.home_team = home_team;
        this.external_team = external_team;
        this.game_id = "GM"+counter++;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
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
}
