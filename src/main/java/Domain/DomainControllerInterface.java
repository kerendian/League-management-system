package Domain;

import Service.Status;

import java.util.HashMap;

public interface DomainControllerInterface
{
    //TODO:add all the signature of the functions from domain controller
    UserStatus findUser(String userName, String password, String userType);
    HashMap<String,String> findGame(String game_id);
    void games_placement(HashMap<String,String> game_details);
    void assign_referee_to_game(String referee_id, String game_id);
    void assign_referee_to_league(String referee_id,String league_id);



}
