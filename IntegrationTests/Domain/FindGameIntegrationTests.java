package Domain;

import DataAccess.DAController;
import DataAccess.DBConnector;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.*;

public class FindGameIntegrationTests {
    //integrating find user from Domain controller with find user from DAController
    static DomainController dc;
    @BeforeClass
    public static void setUp() throws Exception
    {
        dc = new DomainController(DAController.getInstance());
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID,leagueID) VALUES(?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "GAME1");
        stmt.setString(2, "TEAM1");
        stmt.setString(3, "TEAM2");
        stmt.setString(4, "LEAGUE1");
        stmt.executeUpdate();
        stmt.close();
        //_________________________________________________________________
        sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID,leagueID) VALUES(?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "GAME3");
        stmt.setString(2, "TEAM1");
        stmt.setString(3, "TEAM2");
        stmt.setString(4, "LEAGUE1");
        stmt.executeUpdate();
        stmt.close();

    }
}
