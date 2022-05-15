package Service;

import DataAccess.DAController;
import DataAccess.DAControllerInterface;
import DataAccess.DBConnector;
import Domain.DomainController;
import Domain.UserStatus;
import Exceptions.WrongPasswordException;
import Exceptions.WrongUserNameException;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.*;

public class LogInAcceptanceTest {
    private static ServiceController sc;

    @BeforeClass
    public static void setUp() throws Exception
    {
        sc = new ServiceController(new DomainController(DAController.getInstance()));
        //TODO:check if it's better to put it into a function inside DAController (insertUser)
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
    public void logInSuccessful() throws Exception {
        UserStatus res = sc.logIn("Alice","test1234","Referees");
        assertEquals(UserStatus.Valid,res);
    }
    @Test(expected = WrongPasswordException.class)
    public void logInWrongPassword() throws Exception {
        UserStatus res = sc.logIn("Alice","test12","Referees");
    }

    @Test(expected = WrongUserNameException.class)
    public void logInWrongUserName() throws Exception {
        UserStatus res = sc.logIn("Bob","test1234","Referees");
    }
    @Test(expected = WrongUserNameException.class)
    public void logInWrongUserType() throws Exception {
        UserStatus res = sc.logIn("Alice","test1234","UnionRepresentors");
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