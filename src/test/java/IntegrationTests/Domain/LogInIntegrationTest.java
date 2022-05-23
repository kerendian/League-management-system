package IntegrationTests.Domain;

import DataAccess.DAController;
import DataAccess.DBConnector;
import Domain.DomainController;
import Domain.UserStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.*;

public class LogInIntegrationTest {
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

    @Test
    public void findValidUser()
    {
        UserStatus res = dc.findUser("Alice","test1234","Referees");
        assertEquals(UserStatus.Valid,res);
    }
    @Test
    public void findUserWithWrongPassword()
    {
        UserStatus res = dc.findUser("Alice","test12","Referees");
        assertEquals(UserStatus.WrongPassword,res);
    }
    @Test
    public void findUserWithWrongUserName()
    {
        UserStatus res = dc.findUser("Bob","test1234","Referees");
        assertEquals(UserStatus.WrongType,res);
    }
    @Test
    public void findUserWithWrongUserType()
    {
        UserStatus res = dc.findUser("Alice","test1234","UnionRepresentors");
        assertEquals(UserStatus.WrongType,res);
    }
    @AfterClass
    public static void tearDown() throws Exception
    {
        //TODO:check if it's better to put it into a function inside DAController (removeUser)

        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "DELETE FROM Referees WHERE refereeID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "ref1");
        stmt.executeUpdate();
        stmt.close();
        dbc.disconnect(conn);
    }
}