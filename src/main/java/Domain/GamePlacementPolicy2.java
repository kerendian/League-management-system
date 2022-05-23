package Domain;

public class GamePlacementPolicy2 extends GamePlacementPolicy{
    public static Game add_game_to_league(Game game ,String home_court_id,String external_court_id){
        game.setCourtID(home_court_id);
        Game new_game = new Game(game.getExternal_team_ID(),game.getHome_team_ID());
        new_game.setLeagueID(game.getLeagueID());
        new_game.setCourtID(external_court_id);
        //by default the game will be at the next day at the same hour ?
        return new_game;

    }
}
