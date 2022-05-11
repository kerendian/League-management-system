package Domain;

import java.util.ArrayList;

public class League {
   static int counter = 1;
   String league_id;
   ArrayList<Referee> Referees;
   ArrayList<Game> Games;
   Policy scoring_policy;
   Policy game_policy;
   Season league_season;

    public League() {
        league_id = "LEA"+ counter++;
        Referees = new ArrayList<Referee>();
        Games = new ArrayList<Game>();
    }

    public String getLeague_id() {
        return league_id;
    }

    public void setLeague_id(String league_id) {
        this.league_id = league_id;
    }

    public ArrayList<Referee> getReferees() {
        return Referees;
    }

    public ArrayList<Game> getGames() {
        return Games;
    }

    public void setReferees(ArrayList<Referee> referees) {
        Referees = referees;
    }

    public Policy getScoring_policy() {
        return scoring_policy;
    }

    public void setScoring_policy(Policy scoring_policy) {
        this.scoring_policy = scoring_policy;
    }

    public Policy getGame_policy() {
        return game_policy;
    }

    public void setGame_policy(Policy game_policy) {
        this.game_policy = game_policy;
    }

    public Season getLeague_season() {
        return league_season;
    }

    public void setLeague_season(Season league_season) {
        this.league_season = league_season;
    }

    public  void Add_referee(Referee referee){
        Referees.add(referee);
    }

    public void add_game(Game game){Games.add(game); }
}

