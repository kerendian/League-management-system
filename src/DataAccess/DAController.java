package DataAccess;

import Domain.UserStatus;
import Exceptions.ImportDataException;
import Exceptions.ObjectIDNotExistException;
import Service.Status;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DAController {
private static DAController instance = new DAController();
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

    public HashMap<String,String>  findGame(String game_id)
    {
        HashMap<String,String> game_details = new HashMap<>();

        try {
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
                throw new ImportDataException("could not import the game data");
            }
            rs.close();
            stmt.close();
            dbc.disconnect(conn);
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return game_details;
    }


    public HashMap<String,String>  findLeague(String league_id) {
        HashMap<String, String> league_details = new HashMap<>();
        try {
            String sql = "SELECT * FROM Leagues WHERE leagueID = '" + league_id + "';";
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //reading the rows that returned
            if (rs.next()) {
                String league_idFromDB = rs.getString("leagueID");
                if (league_idFromDB.equals(league_id)) {
                    league_details.put("league_id", league_idFromDB);
                    league_details.put("season_year", rs.getString("season_year"));
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return league_details;
    }

    public HashMap<String,String>  findCourt(String court_id) {
        HashMap<String, String> court_details = new HashMap<>();
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return court_details;
    }
//    public void game_placement(HashMap<String,String> game_details){
//        try {
//            String sql = "SELECT * FROM Games WHERE gameID = '" + game_details.get("game_id") + "';";
//            Connection conn = dbc.connect();
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            rs.updateString("date", game_details.get("date"));
//            rs.updateString("hour", game_details.get("hour"));
//            rs.updateString("homeTeam_ID", game_details.get("home_team"));
//            rs.updateString("externalTeam_ID", game_details.get("external_team"));
//            rs.updateString("courtID", game_details.get("court"));
//            rs.updateString("leagueID", game_details.get("league"));
//            rs.updateRow();
//            System.out.println("Row Updated");
//            System.out.println(rs.getMetaData());
//            rs.close();
//            stmt.close();
//            dbc.disconnect(conn);
//        }
//
//        catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//    }

    public void games_placement(HashMap<String,String> game_details){
        try {
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
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("row was updated successfully!");

        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void referees_placement(HashMap<String,String> game_details){
        try {
            String sql = "UPDATE Games " +
                    "SET main_referee_ID = '" + game_details.get("main_referee") + "',"+
                    "secondary_referee_ID1 = '" + game_details.get("secondary_referee_1") + "',"+
                    "secondary_referee_ID2 = '" + game_details.get("secondary_referee_2") + "'"+
                    "WHERE gameID = '" + game_details.get("game_id") + "';";
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("row was updated successfully!");

        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
