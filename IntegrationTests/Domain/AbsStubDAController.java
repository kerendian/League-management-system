package Domain;

import DataAccess.DAControllerInterface;
import DataAccess.DBConnector;
import Exceptions.ImportDataException;
import Exceptions.ObjectIDNotExistException;
import Service.Status;

import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbsStubDAController implements DAControllerInterface {
    DBConnector dbc = DBConnector.getInstance();

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
    public HashMap<String, String> findGame(String game_id) throws ObjectIDNotExistException, SQLException, ImportDataException {
        //good case
        HashMap<String, String> game_row_data1 = new HashMap<>();
        game_row_data1.put("game_id", "GAME1");
        game_row_data1.put("home_team", "TEAM1");
        game_row_data1.put("external_team", "TEAM2");
        game_row_data1.put("league", "LEAGUE1");

        HashMap<String, String> game_row_data3 = new HashMap<>();
        game_row_data3.put("game_id", "GAME3");
        game_row_data3.put("home_team", "TEAM1");
        game_row_data3.put("external_team", "TEAM2");
        game_row_data3.put("league", "LEAGUE1");

        HashMap<String, String> game_row_data4 = new HashMap<>();
        game_row_data4.put("game_id", "GAME4");
        game_row_data4.put("home_team", "TEAM1");
        game_row_data4.put("external_team", "TEAM2");
        game_row_data4.put("league", "LEAGUE1");
        game_row_data4.put("main_referee", "REF1");
        game_row_data4.put("secondary_referee_1", "REF2");

        //bad case - game without league/ game before schedule
        HashMap<String, String> game_row_data2 = new HashMap<>();
        game_row_data2.put("game_id", "GAME2");
        game_row_data2.put("home_team", "TEAM2");
        game_row_data2.put("external_team", "TEAM3");

        //schedule game policy 2
        HashMap<String, String> game_row_data5 = new HashMap<>();
        game_row_data5.put("game_id", "GAME5");
        game_row_data5.put("home_team", "TEAM2");
        game_row_data5.put("external_team", "TEAM1");


        if(game_row_data1.get("game_id").equals(game_id)){
            return game_row_data1;
        }
        else if(game_row_data2.get("game_id").equals(game_id)){
            return game_row_data2;
        }
        else if(game_row_data3.get("game_id").equals(game_id)){
            return game_row_data3;
        }
        else if(game_row_data4.get("game_id").equals(game_id)){
            return game_row_data4;
        }
        else if(game_row_data5.get("game_id").equals(game_id)){
            return game_row_data5;
        }
        else{
            throw new ObjectIDNotExistException("The game id is not found in the DB");
        }
    }


    @Override
    public HashMap<String, String> findReferee(String referee_id) throws ObjectIDNotExistException, SQLException, ImportDataException {
        HashMap<String, String> referee_row_data4 = new HashMap<>();
        referee_row_data4.put("refereeID", "REF4");
        referee_row_data4.put("qualification", "20 YEARS EXPERIENCE");
        referee_row_data4.put("username", "Moshe");
        referee_row_data4.put("password", "123456");
        referee_row_data4.put("refNum", "4");
        referee_row_data4.put("leagueID", null);


        HashMap<String, String> referee_row_data1 = new HashMap<>();
        referee_row_data1.put("refereeID", "REF1");
        referee_row_data1.put("qualification", "20 YEARS EXPERIENCE");
        referee_row_data1.put("username", "Moshe");
        referee_row_data1.put("password", "123456");
        referee_row_data1.put("refNum", "1");
        referee_row_data1.put("leagueID", "LEAGUE1");

        HashMap<String, String> referee_row_data2 = new HashMap<>();
        referee_row_data2.put("refereeID", "REF2");
        referee_row_data2.put("qualification", "20 YEARS EXPERIENCE");
        referee_row_data2.put("username", "Moshe");
        referee_row_data2.put("password", "123456");
        referee_row_data2.put("refNum", "2");
        referee_row_data2.put("leagueID", "LEAGUE1");

        HashMap<String, String> referee_row_data3 = new HashMap<>();
        referee_row_data3.put("refereeID", "REF3");
        referee_row_data3.put("qualification", "22 YEARS EXPERIENCE");
        referee_row_data3.put("username", "David");
        referee_row_data3.put("password", "123456");
        referee_row_data3.put("refNum", "3");
        referee_row_data3.put("leagueID", "LEAGUE1");

        if(referee_row_data1.get("refereeID").equals(referee_id)){
            return referee_row_data1;
        }
        else if(referee_row_data2.get("refereeID").equals(referee_id)){
            return referee_row_data2;
        }
        else if(referee_row_data3.get("refereeID").equals(referee_id)){
            return referee_row_data3;
        }
        else if(referee_row_data4.get("refereeID").equals(referee_id)){
            return referee_row_data4;
        }
        else{
            throw new ObjectIDNotExistException("The referee id is not found in the DB");
        }

    }

    @Override
    public HashMap<String, String> findLeague(String league_id) throws ObjectIDNotExistException, SQLException {
        HashMap<String, String> league_row_data = new HashMap<>();
        league_row_data.put("league_id", "LEAGUE1");
        league_row_data.put("policy_id", "POLICY1");
        league_row_data.put("season_id", "SEASON1");

        HashMap<String, String> league_row_data2 = new HashMap<>();
        league_row_data2.put("league_id", "LEAGUE2");
        league_row_data2.put("policy_id", "POLICY2");
        league_row_data2.put("season_id", "SEASON1");

        if(league_row_data.get("league_id").equals(league_id)){
            return league_row_data;
        }
        else  if(league_row_data2.get("league_id").equals(league_id)){
            return league_row_data2;
        }
        else{
            throw new ObjectIDNotExistException("The league id is not found in the DB");
        }

    }

    @Override
    public HashMap<String, String> findCourt(String court_id) throws ObjectIDNotExistException, SQLException {
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
    public Status games_placement(HashMap<String, String> game_details) throws SQLException {
        return Status.success;
    }

    @Override
    public Status updateRefereesToGame(HashMap<String, String> game_details) throws SQLException {
        if(game_details.get("main_referee").equals("REF1") && game_details.get("game_id").equals("GAME1")){
            return Status.success;
        }
        else if(game_details.get("secondary_referee_1").equals("REF1") && game_details.get("game_id").equals("GAME3")){
            return Status.success;
        }
        else if(game_details.get("secondary_referee_2").equals("REF3") && game_details.get("game_id").equals("GAME4")){
            return Status.success;
        }

        return Status.failure;
    }

    @Override
    public Status updateLeagueToReferee(String referee_id, String league_id) throws SQLException {
        if (league_id.equals("LEAGUE1") && referee_id.equals("REF4")){
            return Status.success;
        }
        return Status.failure;
    }

    @Override
    public HashMap<String, String> findTeam(String team_id) throws ObjectIDNotExistException, SQLException {

        HashMap<String, String> team_row_data1 = new HashMap<>();
        HashMap<String, String> team_row_data2 = new HashMap<>();
        team_row_data1.put("team_id", "TEAM1");
        team_row_data1.put("court_id", "COURT1");
        team_row_data2.put("team_id", "TEAM2");
        team_row_data2.put("court_id", "COURT2");

        HashMap<String, String> team_row_data3 = new HashMap<>();
        team_row_data3.put("team_id", "TEAM3");
        team_row_data3.put("court_id", "COURT3");

        if(team_row_data1.get("team_id").equals(team_id)){
            return team_row_data1;
        }
        else if(team_row_data2.get("team_id").equals(team_id)){
            return team_row_data2;
        }
        else if(team_row_data3.get("team_id").equals(team_id)){
            return team_row_data3;
        }
        else{
            throw new ObjectIDNotExistException("The Team id is not found in the DB");
        }
    }

    @Override
    public boolean check_game_date_validation(String team_id, String date) throws SQLException {
        if(team_id.equals("TEAM2")||team_id.equals("TEAM3")){
            return true;
        }
        else{
            return false;
        }
    }
}
