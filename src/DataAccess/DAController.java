package DataAccess;

import Domain.UserStatus;
import Exceptions.ImportDataException;
import Exceptions.ObjectIDNotExistException;
import Service.Status;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DAController implements DAControllerInterface {
private static DAController instance = new DAController() ;
DBConnector dbc = DBConnector.getInstance();
private DAController(){}

public static DAController getInstance(){return  instance;};
    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus  us = null;
        try {
            String sql = "SELECT * FROM "+ userType + " WHERE userName = '" + userName + "';" ;
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                String passFromDB = rs.getString("password");
                if (passFromDB.equals(password))
                {
                    us = UserStatus.Valid;
                }
                else
                {
                    us = UserStatus.WrongPassword;
                }
            }
            else
            {
                us = UserStatus.WrongType;
            }
            rs.close();
            stmt.close();
            dbc.disconnect(conn);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    return us;
    }

    public HashMap<String,String>  findGame(String game_id) throws SQLException, ObjectIDNotExistException, ImportDataException {
        HashMap<String,String> game_details = new HashMap<>();

        //try {
            String sql = "SELECT * FROM Games WHERE gameID = '" + game_id + "';" ;
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //reading the rows that returned
            if (rs.next())
            {
                String game_idFromDB = rs.getString("gameID");
                if (game_idFromDB.equals(game_id))
                {   game_details.put("game_id",rs.getString("gameID"));
                    game_details.put("date",rs.getString("date"));
                    game_details.put("hour",rs.getString("hour"));
                    game_details.put("home_team",rs.getString("homeTeam_ID"));
                    game_details.put("external_team",rs.getString("externalTeam_ID"));
                    game_details.put( "main_referee" ,   rs.getString("main_referee_ID"));
                    game_details.put( "secondary_referee_1" ,   rs.getString("secondary_referee_ID1"));
                    game_details.put(  "secondary_referee_2",   rs.getString("secondary_referee_ID2"));
                    game_details.put(  "court", rs.getString("courtID"));
                    game_details.put( "league" , rs.getString("leagueID"));
                    game_details.put(  "result", rs.getString("result"));


                }
                else
                {
                    game_details = null;
                    rs.close();
                    stmt.close();
                    dbc.disconnect(conn);
                    throw new ObjectIDNotExistException("The game id is not found in the DB");
                }
            }
            else
            {
                rs.close();
                stmt.close();
                dbc.disconnect(conn);
                throw new ObjectIDNotExistException("The game id is not found in the DB");
            }
            rs.close();
            stmt.close();
            dbc.disconnect(conn);
        //}

        //catch (Exception e)
        //{
            //System.out.println(e.getMessage());
        //}
        return game_details;
    }


    //bring referee details without games
    public HashMap<String,String>  findReferee(String referee_id) throws SQLException, ObjectIDNotExistException, ImportDataException {
        HashMap<String,String> referee_details = new HashMap<>();

        //try {
            String sql = "SELECT * FROM Referees WHERE refereeID = '" + referee_id + "';" ;
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //reading the rows that returned
            if (rs.next())
            {
                String referee_idFromDB = rs.getString("refereeID");
                if (referee_idFromDB.equals(referee_id))
                {   referee_details.put("refereeID",rs.getString("refereeID"));
                    referee_details.put("refNum",rs.getString("refNum"));
                    referee_details.put("leagueID",rs.getString("leagueID"));
                    referee_details.put("qualification",rs.getString("qualification"));
                    referee_details.put("username",rs.getString("userName"));
                    referee_details.put("password",rs.getString("password"));

                }
                else
                {
                    rs.close();
                    stmt.close();
                    dbc.disconnect(conn);
                    throw new ObjectIDNotExistException("The referee id is not found in the DB");
                }
            }
            else
            {
                rs.close();
                stmt.close();
                dbc.disconnect(conn);
                throw new ObjectIDNotExistException("The referee id is not found in the DB");
            }
            rs.close();
            stmt.close();
            dbc.disconnect(conn);

        return referee_details;
    }


    public HashMap<String,String>  findLeague(String league_id) throws SQLException, ObjectIDNotExistException {
        HashMap<String, String> league_details = new HashMap<>();
        //try {
            String sql = "SELECT * FROM Leagues WHERE leagueID = '" + league_id + "';";
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //reading the rows that returned
            if (rs.next()) {
                String league_idFromDB = rs.getString("leagueID");
                if (league_idFromDB.equals(league_id)) {
                    league_details.put("league_id", league_idFromDB);
                    league_details.put("season_id", rs.getString("seasonID"));
                    league_details.put("policy_id", rs.getString("policyID"));

                }
                else{
                    System.out.println("The retrieval from the database was performed but there is no equality between the line and the argument");
                }


                rs.close();
                stmt.close();
                dbc.disconnect(conn);
            }
            else{
                rs.close();
                stmt.close();
                dbc.disconnect(conn);
                throw new ObjectIDNotExistException("The league id is not found in the DB");
            }
        //} catch (Exception e) {
            //System.out.println(e.getMessage());
        //}
        return league_details;
    }

    public HashMap<String,String>  findCourt(String court_id) throws SQLException, ObjectIDNotExistException {
        HashMap<String, String> court_details = new HashMap<>();
        //try {
            String sql = "SELECT * FROM Courts WHERE courtID = '" + court_id + "';";
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //reading the rows that returned
            if (rs.next()) {
                String court_idFromDB = rs.getString("courtID");
                if (court_idFromDB.equals(court_id)) {
                    court_details.put("court_id", court_idFromDB);
                    court_details.put("name", rs.getString("name"));

                }
                else{
                    System.out.println("The retrieval from the database was performed but there is no equality between the line and the argument");
                }


                rs.close();
                stmt.close();
                dbc.disconnect(conn);
            }
            else{
                rs.close();
                stmt.close();
                dbc.disconnect(conn);
                throw new ObjectIDNotExistException("The league id is not found in the DB");
            }
        //} catch (Exception e) {
            //System.out.println(e.getMessage());
        //}
        return court_details;
    }

    public Status games_placement(HashMap<String,String> game_details) throws SQLException {
        //try {
            String sql = "UPDATE Games " +
                    "SET date = '" + game_details.get("date") + "',"+
                    "hour = '" + game_details.get("hour") + "',"+
                    "homeTeam_ID = '" + game_details.get("home_team") + "',"+
                    "externalTeam_ID = '" + game_details.get("external_team") + "',"+
                    "courtID = '" + game_details.get("court") + "',"+
                    "leagueID = '" + game_details.get("league") + "'"+
                    "WHERE gameID = '" + game_details.get("game_id") + "';";
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            boolean rs = stmt.execute(sql);
            System.out.println("row was updated successfully!");
            stmt.close();
            dbc.disconnect(conn);
            return Status.success;

        //}

        //catch (Exception e) {
            //System.out.println(e.getMessage());
            //return Status.failure;
       // }
    }

    public Status updateRefereesToGame(HashMap<String,String> game_details) throws SQLException {
        //try {
            String sql = "UPDATE Games " +
                    "SET main_referee_ID = '" + game_details.get("main_referee") + "',"+
                    "secondary_referee_ID1 = '" + game_details.get("secondary_referee_1") + "',"+
                    "secondary_referee_ID2 = '" + game_details.get("secondary_referee_2") + "'"+
                    "WHERE gameID = '" + game_details.get("game_id") + "';";
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            boolean rs = stmt.execute(sql);
            System.out.println("row was updated successfully!");
            stmt.close();
            dbc.disconnect(conn);
            return Status.success;

        //}

       // catch (Exception e) {
           // System.out.println(e.getMessage());
           // return Status.failure;
       // }
    }


    public Status updateLeagueToReferee(String referee_id, String league_id) throws SQLException {

        //try {
            String sql = "UPDATE Referees " +
                    "SET leagueID = '" + league_id + "'," +
                    "WHERE refereeID = '" + referee_id + "';";
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("row was updated successfully!");
            stmt.close();
            dbc.disconnect(conn);
            return Status.success;

        //} catch (Exception e) {
           //System.out.println(e.getMessage());
           // return Status.failure;
        //}

    }


    public HashMap<String,String>  findTeam(String team_id) throws SQLException, ObjectIDNotExistException {
        HashMap<String, String> team_details = new HashMap<>();
        //try {
            String sql = "SELECT * FROM Teams WHERE teamID = '" + team_id + "';";
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //reading the rows that returned
            if (rs.next()) {
                String team_idFromDB = rs.getString("teamID");
                if (team_idFromDB.equals(team_id)) {
                    team_details.put("team_id", team_idFromDB);
                    team_details.put("court_id", rs.getString("team_courtID"));

                }
                else{
                    System.out.println("The retrieval from the database was performed but there is no equality between the line and the argument");
                }


                rs.close();
                stmt.close();
                dbc.disconnect(conn);
            }
            else{
                rs.close();
                stmt.close();
                dbc.disconnect(conn);
                throw new ObjectIDNotExistException("The league id is not found in the DB");
            }
        //} catch (Exception e) {
           // System.out.println(e.getMessage());
        //}
        return team_details;
    }

    public boolean check_game_date_validation(String team_id, String date) throws SQLException {
        String sql = "SELECT * FROM Games WHERE (homeTeam_ID = '" + team_id + "'"+
                "OR externalTeam_ID = '" + team_id + "')"+
                "AND date = '" + date + "';";
        Connection conn = dbc.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        //reading the rows that returned
        if (rs.next()) {
            rs.close();
            stmt.close();
            dbc.disconnect(conn);
            return false;
            }
            else {
            rs.close();
            stmt.close();
            dbc.disconnect(conn);
            return true;
        }
    }
}
