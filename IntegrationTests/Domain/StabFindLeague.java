package Domain;

import Exceptions.ObjectIDNotExistException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class StabFindLeague extends AbsStubDAController {



    @Override
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


}
