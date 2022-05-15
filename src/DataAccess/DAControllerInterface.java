package DataAccess;

import Domain.UserStatus;
import Service.Status;

import java.util.HashMap;

public interface DAControllerInterface {

    UserStatus findUser(String userName, String password, String userType);
    HashMap<String,String>  findGame(String game_id);
    HashMap<String,String>  findReferee(String referee_id);
    HashMap<String,String>  findLeague(String league_id);
    HashMap<String,String>  findCourt(String court_id);
    Status games_placement(HashMap<String,String> game_details);
    Status updateRefereesToGame(HashMap<String,String> game_details);
    Status updateLeagueToReferee(String referee_id, String league_id);
    HashMap<String,String>  findTeam(String team_id);




}
