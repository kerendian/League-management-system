package Domain;

import Exceptions.ObjectIDNotExistException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class StabFindCourt extends AbsStubDAController {

    @Override

    public HashMap<String,String>  findCourt(String court_id) throws SQLException, ObjectIDNotExistException {
        HashMap<String, String> court_details = new HashMap<>();
        //try {
        String sql = "SELECT * FROM Courts WHERE courtID = '" + court_id + "';";
        Connection conn = dbc.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        //reading the rows that returned
        if (rs.next()) {
            String court_idFromDB = rs.getString("courtID");
            if (court_idFromDB.equals(court_id)) {
                court_details.put("court_id", court_idFromDB);
                court_details.put("name", rs.getString("name"));

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

        return court_details;
    }

}
