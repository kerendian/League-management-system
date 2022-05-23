package Domain;
//good
import Exceptions.ImportDataException;
import Exceptions.ObjectIDNotExistException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class StubFindReferee extends AbsStubDAController{

    //bring referee details without games
    @Override
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
}
