package AcceptanceTests.Service;

import DataAccess.DAController;
import DataAccess.DBConnector;
import Domain.DomainController;

import Exceptions.ImportDataException;
import Exceptions.ObjectIDNotExistException;
import Exceptions.ScheduleRefereeFailed;
import Service.ServiceController;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AssignRefereeToLeagueAcceptanceTest {


    private static ServiceController sc;

    @BeforeClass
    public static void setUp() throws Exception {

        sc = new ServiceController(new DomainController(DAController.getInstance()));
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();

        String sql = "DELETE FROM Referees";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();

        sql = "DELETE FROM Leagues";
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();


        sql = "INSERT INTO Leagues(leagueID,seasonID,policyID) VALUES(?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "LEAGUE1");
        stmt.setString(2, "SEASON1");
        stmt.setString(3, "POLICY1");
        stmt.executeUpdate();
        stmt.close();
        sql = "INSERT INTO Referees(refereeID,qualification,userName,password,refNum,leagueID) VALUES(?,?,?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "REF1");
        stmt.setString(2, "20 YEARS EXPERIENCE");
        stmt.setString(3, "Moshe1");
        stmt.setString(4, "123456");
        stmt.setString(5, "1");
        stmt.setString(6, "LEAGUE1");
        stmt.executeUpdate();
        stmt.close();
        sql = "INSERT INTO Referees(refereeID,qualification,userName,password,refNum) VALUES(?,?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "REF4");
        stmt.setString(2, "20 YEARS EXPERIENCE");
        stmt.setString(3, "david2");
        stmt.setString(4, "123456");
        stmt.setString(5, "10");

        stmt.executeUpdate();
        stmt.close();
        dbc.disconnect(conn);
    }

    //============ assign referee to league ===========


    @Test(expected = ObjectIDNotExistException.class)
    public void assign_invalid_referee_to_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        //(-)
        HashMap<String,String>  res = sc.assign_referee_to_league("REF7","LEAGUE1");
    }

    @Test(expected = ObjectIDNotExistException.class)
    public void assign_referee_to_invalid_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        HashMap<String,String>  res = sc.assign_referee_to_league("REF4","LEAGUE7");
    }

    @Test(expected = ScheduleRefereeFailed.class)
    public void assign_referee_with_league_to_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        HashMap<String,String>  res = sc.assign_referee_to_league("REF1","LEAGUE1");
    }

    @Test
    public void assign_valid_referee_to_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        //(+)
        HashMap<String,String>  referee_details = sc.assign_referee_to_league("REF4","LEAGUE1");
        assertEquals("REF4",referee_details.get("refereeID"));
        assertEquals("LEAGUE1",referee_details.get("leagueID"));
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();

        String sql = "DELETE FROM Referees";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();

        sql = "DELETE FROM Leagues";
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();

        dbc.disconnect(conn);
    }


}