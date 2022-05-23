package Domain;
//good
import Exceptions.ImportDataException;
import Exceptions.ObjectIDNotExistException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class StubFindGame extends AbsStubDAController{

    @Override
    public HashMap<String,String> findGame(String game_id) throws SQLException, ObjectIDNotExistException, ImportDataException {
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

        return game_details;
    }
}
