package DataAccess;

import Domain.UserStatus;
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
                    game_details.put("game_id",rs.getString("date"));


                     String date = rs.getString("date");
                     String hour = rs.getString("hour");
                     String home_team = rs.getString("homeTeam_ID");
                     String external_team = rs.getString("externalTeam_ID");
                     String main_referee = rs.getString("main_referee_ID");
                     String secondary_referee_ID1 = rs.getString("secondary_referee_ID1");
                     String secondary_referee_ID2 = rs.getString("secondary_referee_ID2");
                     String court_id = rs.getString("courtID");
                     String league_id = rs.getString("leagueID");




                    main_referee_ID
                            main_referee,,secondary_referee1, secondary_referee2, court_id, leasgue_id]



                }
                else
                {
                    game_details = Status.WrongPassword;
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
}
