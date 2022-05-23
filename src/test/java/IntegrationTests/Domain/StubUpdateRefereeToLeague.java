//good
package IntegrationTests.Domain;
import Service.Status;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class StubUpdateRefereeToLeague extends AbsStubDAController {

    @Override
    public Status updateLeagueToReferee(String referee_id, String league_id) throws SQLException {

        //try {
        String sql = "UPDATE Referees " +
                "SET leagueID = '" + league_id + "'" +
                "WHERE refereeID = '" + referee_id + "';";
        Connection conn = dbc.connect();
        Statement stmt = conn.createStatement();
        boolean rs = stmt.execute(sql);
        stmt.close();
        dbc.disconnect(conn);
        return Status.success;

    }
}