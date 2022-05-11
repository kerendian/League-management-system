package Domain;

import java.util.ArrayList;

public abstract class Policy {
    ArrayList<League> league_list;

    public Policy() {
        this.league_list = new ArrayList<League>();
    }

    public void add_league(League league){
        league_list.add(league);
    }
}
