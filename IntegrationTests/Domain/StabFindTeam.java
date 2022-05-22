package Domain;

import Exceptions.ObjectIDNotExistException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class StabFindTeam extends AbsStubDAController{

    @Override
    public HashMap<String,String> findTeam(String team_id) throws SQLException, ObjectIDNotExistException {
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
                team_details.put("court_id", rs.getString("courtID"));

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



}
