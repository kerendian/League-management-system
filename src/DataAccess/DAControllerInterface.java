package DataAccess;

import Domain.UserStatus;

import java.util.HashMap;

public interface DAControllerInterface {

    UserStatus findUser(String userName, String password, String userType);
    HashMap<String,String> findGame(String game_id);
    HashMap<String,String>  findLeague(String league_id);
    HashMap<String,String>  findCourt(String court_id);
    void games_placement(HashMap<String,String> game_details);
    void referees_placement(HashMap<String,String> game_details);

}
