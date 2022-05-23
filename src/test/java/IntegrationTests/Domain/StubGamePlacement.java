package IntegrationTests.Domain;

import Service.Status;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class StubGamePlacement extends AbsStubDAController{

    @Override
    public Status games_placement(HashMap<String,String> game_details) throws SQLException {
        //try {
        String sql = "UPDATE Games " +
                "SET date = '" + game_details.get("date") + "',"+
                "hour = '" + game_details.get("hour") + "',"+
                "homeTeam_ID = '" + game_details.get("home_team") + "',"+
                "externalTeam_ID = '" + game_details.get("external_team") + "',"+
                "courtID = '" + game_details.get("court") + "',"+
                "leagueID = '" + game_details.get("league") + "'"+
                "WHERE gameID = '" + game_details.get("game_id") + "';";
        Connection conn = dbc.connect();
        Statement stmt = conn.createStatement();
        boolean rs = stmt.execute(sql);
        System.out.println("row was updated successfully!");
        stmt.close();
        dbc.disconnect(conn);
        return Status.success;

        //}

        //catch (Exception e) {
        //System.out.println(e.getMessage());
        //return Status.failure;
        // }
    }


}
