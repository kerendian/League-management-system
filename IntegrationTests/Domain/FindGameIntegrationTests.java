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
        String sql = "INSERT INTO Referees(refereeID,userName,password) VALUES(?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "ref1");
        stmt.setString(2, "Alice");
        stmt.setString(3, "test1234");
        stmt.executeUpdate();
        stmt.close();
        dbc.disconnect(conn);

    }
}
