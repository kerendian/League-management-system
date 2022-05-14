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
        // game_details=[game_id,date,hour,home_team,external_team,result,main_referee,,secondary_referee1, secondary_referee2, court_id, leasgue_id]
        //ArrayList<String>  game_details = new ArrayList<>();
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
                game_details= null;
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

                //exe??

                rs.close();
                stmt.close();
                dbc.disconnect(conn);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return league_details;
    }
//    public HashMap<String,String>  findReferee(String referee_id)
//    {
//
//        HashMap<String,String> referee_details = new HashMap<>();
//        try {
//            String sql = "SELECT * FROM Referees WHERE refereeID = '" + referee_id + "';" ;
//            Connection conn = dbc.connect();
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            //reading the rows that returned
//            if (rs.next())
//            {
//                String game_idFromDB = rs.getString("gameID");
//                if (game_idFromDB.equals(game_id))
//                {   game_details.put("game_id",rs.getString("gameID"));
//                    game_details.put("date",rs.getString("date"));
//                    game_details.put("hour",rs.getString("hour"));
//                    game_details.put("home_team",rs.getString("homeTeam_ID"));
//                    game_details.put("external_team",rs.getString("externalTeam_ID"));
//                    game_details.put("internal_team",   rs.getString("internalTeam_ID"));
//                    game_details.put( "main_referee" ,   rs.getString("main_referee_ID"));
//                    game_details.put( "secondary_referee_1" ,   rs.getString("secondary_referee_ID1"));
//                    game_details.put(  "secondary_referee_2",   rs.getString("secondary_referee_ID2"));
//                    game_details.put(  "court", rs.getString("courtID"));
//                    game_details.put( "league" , rs.getString("leagueID"));
//                    game_details.put(  "result", rs.getString("result"));
//
//
//                }
//                else
//                {
//                    game_details = null;
//                    throw new GameIDNotExistException("The game id is not found in the DB");
//                }
//            }
//            else
//            {
//                game_details= null;
//                throw new ImportDataException("could not import the game data");
//            }
//            rs.close();
//            stmt.close();
//            dbc.disconnect(conn);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//        }
//        return game_details;
//    }
}
