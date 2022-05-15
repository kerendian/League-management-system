package Domain;

import java.util.ArrayList;

public abstract class Policy {
    //ArrayList<League> league_list;
    String policy_id;
    int counter = 3;

    public Policy() {
        //this.league_list = new ArrayList<League>();
        this.policy_id = "POLICY"+counter++;
    }

    //public void add_league(League league){
        //league_list.add(league);
    }
}
