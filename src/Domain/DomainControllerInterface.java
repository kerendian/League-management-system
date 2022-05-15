package Domain;

import Service.Status;

import java.util.HashMap;

public interface DomainControllerInterface
{
    //TODO:add all the signature of the functions from domain controller
    UserStatus findUser(String userName, String password, String userType);
    HashMap<String,String> findGame(String game_id);
    Status games_placement(String date, int hour , String leagueID,String game_id);
    Status assign_referee_to_game(String referee_id, String game_id,int ref_type);
    Status assign_referee_to_league(String referee_id,String league_id);



}
