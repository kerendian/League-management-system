package Domain;

import DataAccess.DAControllerInterface;
import Exceptions.ObjectIDNotExistException;
import Service.Status;

import java.util.HashMap;

public class StubDAController implements DAControllerInterface
{
    public UserStatus findUser(String userName, String password, String userType)
    {
        if (userName.equals("Alice") && userType.equals("Referees"))
        {
            if (password.equals("test1234"))
            {
                return UserStatus.Valid;
            }
            else
            {
                return UserStatus.WrongPassword;
            }
        }
        else
        {
            return UserStatus.WrongType;
        }
    }

    @Override
    public HashMap<String, String> findGame(String game_id) throws ObjectIDNotExistException {
        HashMap<String, String> game_row_data = new HashMap<>();
        game_row_data.put("game_id", "GAME1");
        game_row_data.put("home_team", "TEAM1");
        game_row_data.put("external_team", "TEAM2");
        if(game_row_data.get("game_id").equals(game_id)){
            return game_row_data;
        }
        else{
            throw new ObjectIDNotExistException("The game id is not found in the DB");
        }
    }


    @Override
    public HashMap<String, String> findReferee(String referee_id) throws ObjectIDNotExistException {
        HashMap<String, String> referee_row_data = new HashMap<>();
        referee_row_data.put("refereeID", "REF1");
        referee_row_data.put("qualification", "20 YEARS EXPERIENCE");
        referee_row_data.put("username", "Moshe");
        referee_row_data.put("password", "123456");
        referee_row_data.put("refNum", "1");
        referee_row_data.put("leagueID", "");



        if(referee_row_data.get("refereeID").equals(referee_id)){
            return referee_row_data;
        }
        else{
            throw new ObjectIDNotExistException("The referee id is not found in the DB");
        }

    }

    @Override
    public HashMap<String, String> findLeague(String league_id) throws ObjectIDNotExistException {
        HashMap<String, String> league_row_data = new HashMap<>();
        league_row_data.put("league_id", "LEAGUE1");
        league_row_data.put("policy_id", "POLICY1");
        league_row_data.put("season_id", "SEASON1");

        if(league_row_data.get("refereeID").equals(league_id)){
            return league_row_data;
        }
        else{
            throw new ObjectIDNotExistException("The league id is not found in the DB");
        }

    }

    @Override
    public HashMap<String, String> findCourt(String court_id) throws ObjectIDNotExistException {
        HashMap<String, String> court_row_data = new HashMap<>();
        court_row_data.put("court_id", "COURT1");
        court_row_data.put("name", "Ramat Gan stadium");

        if(court_row_data.get("court_id").equals(court_id)){
            return court_row_data;
        }
        else{
            throw new ObjectIDNotExistException("The court id is not found in the DB");
        }
    }

    @Override
    public Status games_placement(HashMap<String, String> game_details) {
        return Status.success;
    }

    @Override
    public Status updateRefereesToGame(HashMap<String, String> game_details) {

        return Status.success;
    }

    @Override
    public Status updateLeagueToReferee(String referee_id, String league_id) {
        if (league_id.equals("LEAGUE1") && referee_id.equals("REF1")){
            return Status.success;
        }
        return Status.failure;
    }

    @Override
    public HashMap<String, String> findTeam(String team_id) throws ObjectIDNotExistException {

        HashMap<String, String> team_row_data1 = new HashMap<>();
        HashMap<String, String> team_row_data2 = new HashMap<>();
        team_row_data1.put("team_id", "TEAM1");
        team_row_data1.put("court_id", "Ramat Gan Stadium");
        team_row_data2.put("team_id", "TEAM2");
        team_row_data2.put("court_id", "Tel Aviv");

        if(team_row_data1.get("team_id").equals(team_id)){
            return team_row_data1;
        }
        else if(team_row_data2.get("team_id").equals(team_id)){
            return team_row_data2;
        }
        else{
            throw new ObjectIDNotExistException("The Team id is not found in the DB");
        }
    }

}
