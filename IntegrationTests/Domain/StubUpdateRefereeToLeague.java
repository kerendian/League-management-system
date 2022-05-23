//good
package Domain;
import Exceptions.ImportDataException;
import Exceptions.ObjectIDNotExistException;
import Service.Status;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
public class StubUpdateRefereeToLeague extends AbsStubDAController{

    @Override
    public Status updateLeagueToReferee(String referee_id, String league_id) throws SQLException {

        //try {
        String sql = "UPDATE Referees " +
                "SET leagueID = '" + league_id + "'," +
                "WHERE refereeID = '" + referee_id + "';";
        Connection conn = dbc.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        //System.out.println("row was updated successfully!");
        stmt.close();
        dbc.disconnect(conn);
        return Status.success;

    }
}
