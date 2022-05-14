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
                    return league_details;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return league_details;
    }
}
