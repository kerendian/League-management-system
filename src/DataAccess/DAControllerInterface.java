package DataAccess;

import Domain.UserStatus;
import Exceptions.ImportDataException;
import Exceptions.ObjectIDNotExistException;
import Service.Status;

import java.sql.SQLException;
import java.util.HashMap;

public interface DAControllerInterface {

    UserStatus findUser(String userName, String password, String userType);
    HashMap<String,String>  findGame(String game_id) throws ObjectIDNotExistException, SQLException, ImportDataException;
    HashMap<String,String>  findReferee(String referee_id) throws ObjectIDNotExistException, SQLException, ImportDataException;
    HashMap<String,String>  findLeague(String league_id) throws ObjectIDNotExistException, SQLException;
    HashMap<String,String>  findCourt(String court_id) throws ObjectIDNotExistException, SQLException;
    Status games_placement(HashMap<String,String> game_details) throws SQLException;
    Status updateRefereesToGame(HashMap<String,String> game_details) throws SQLException;
    Status updateLeagueToReferee(String referee_id, String league_id) throws SQLException;
    HashMap<String,String>  findTeam(String team_id) throws ObjectIDNotExistException, SQLException;
    boolean check_game_date_validation(String team_id, String date) throws SQLException;




}
