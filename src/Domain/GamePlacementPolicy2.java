package Domain;

public class GamePlacementPolicy2 extends GamePlacementPolicy{
    public void add_game_to_league(League league, Game game){
        for (League my_league: this.league_list) {
            if(league.getLeague_id()==my_league.getLeague_id()){
                league.getGames().add(game);
                league.getGames().add(game);
            }
            else{
                System.out.println("league dont have this policy");
            }

        }
    }
}
