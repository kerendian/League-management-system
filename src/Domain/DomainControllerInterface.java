package Domain;

import Exceptions.ImportDataException;
import Exceptions.InvalidDateException;
import Exceptions.NullPointerException;
import Exceptions.ObjectIDNotExistException;
import Exceptions.ScheduleRefereeFailed;
import Service.Status;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public interface DomainControllerInterface
{
    //TODO:add all the signature of the functions from domain controller
    UserStatus findUser(String userName, String password, String userType);
    //HashMap<String,String> findGame(String game_id);
    ArrayList<HashMap<String,String>> games_placement(String date, int hour , String leagueID, String game_id) throws ObjectIDNotExistException, SQLException, ImportDataException, ParseException, InvalidDateException, ScheduleRefereeFailed;
    HashMap<String,String> assign_referee_to_game(String referee_id, String game_id,int ref_type) throws ObjectIDNotExistException, SQLException, ImportDataException, NullPointerException, ScheduleRefereeFailed;
    Status assign_referee_to_league(String referee_id,String league_id);



}
