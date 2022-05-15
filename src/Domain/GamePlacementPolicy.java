package Domain;

public abstract class GamePlacementPolicy extends Policy {
    public static Game add_game_to_league(Game game,String home_court_id,String external_court_id){
        game.setCourtID(home_court_id);
        return game;
    }
}
