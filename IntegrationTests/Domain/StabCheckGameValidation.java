package Domain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StabCheckGameValidation extends AbsStubDAController{

    @Override
    public boolean check_game_date_validation(String team_id, String date) throws SQLException {
        String sql = "SELECT * FROM Games WHERE (homeTeam_ID = '" + team_id + "'"+
                "OR externalTeam_ID = '" + team_id + "')"+
                "AND date = '" + date + "';";
        Connection conn = dbc.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        //reading the rows that returned
        if (rs.next()) {
            rs.close();
            stmt.close();
            dbc.disconnect(conn);
            return false;
        }
        else {
            rs.close();
            stmt.close();
            dbc.disconnect(conn);
            return true;
        }
    }
}
}
