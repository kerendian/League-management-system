package Domain;

import java.util.ArrayList;

public class League {
   static int counter = 3;
   String league_id;
   String scoring_policyID;
   String game_policyID;
   String seasonID;

    public League() {
        league_id = "LEAGUE"+ counter++;

    }

    public String getLeague_id() {
        return league_id;
    }

    public void setLeague_id(String league_id) {
        this.league_id = league_id;
    }

    public String getScoring_policyID() {
        return scoring_policyID;
    }

    public void setScoring_policyID(String scoring_policyID) {
        this.scoring_policyID = scoring_policyID;
    }

    public String getGame_policyID() {
        return game_policyID;
    }

    public void setGame_policyID(String game_policyID) {
        this.game_policyID = game_policyID;
    }

    public String getSeasonID() {
        return seasonID;
    }


}

