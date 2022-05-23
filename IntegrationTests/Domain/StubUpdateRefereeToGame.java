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

public class StubUpdateRefereeToGame extends AbsStubDAController{

    @Override
    public Status updateRefereesToGame(HashMap<String,String> game_details) throws SQLException {
        //try {
        String sql = "UPDATE Games " +
                "SET main_referee_ID = '" + game_details.get("main_referee") + "',"+
                "secondary_referee_ID1 = '" + game_details.get("secondary_referee_1") + "',"+
                "secondary_referee_ID2 = '" + game_details.get("secondary_referee_2") + "'"+
                "WHERE gameID = '" + game_details.get("game_id") + "';";
        Connection conn = dbc.connect();
        Statement stmt = conn.createStatement();
        boolean rs = stmt.execute(sql);
        //System.out.println("row was updated successfully!");
        stmt.close();
        dbc.disconnect(conn);
        return Status.success;

    }

}
