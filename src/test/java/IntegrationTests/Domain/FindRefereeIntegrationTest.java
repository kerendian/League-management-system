//2

package IntegrationTests.Domain;
//good
import DataAccess.DAControllerInterface;
import DataAccess.DBConnector;
import Domain.DomainController;
import Exceptions.*;
import Exceptions.NullPointerException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.HashMap;

import static org.junit.Assert.*;

public class FindRefereeIntegrationTest {
    //integrating find referee from Domain controller with find referee from DAController
    static DomainController dc;
    @BeforeClass
    public static void setUp() throws Exception
    {
        DAControllerInterface my_stub = new StubFindReferee();
        dc = new DomainController(my_stub);
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "DELETE FROM Referees";
        PreparedStatement stmt = conn.prepareStatement(sql);
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
        //_________________________________________________________________
        sql = "INSERT INTO Referees(refereeID,qualification,userName,password,refNum,leagueID) VALUES(?,?,?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "REF2");
        stmt.setString(2, "20 YEARS EXPERIENCE");
        stmt.setString(3, "Moshe2");
        stmt.setString(4, "123456");
        stmt.setString(5, "2");
        stmt.setString(6, "LEAGUE1");
        stmt.executeUpdate();
        stmt.close();
        //_________________________________________________________________
        sql = "INSERT INTO Referees(refereeID,qualification,userName,password,refNum,leagueID) VALUES(?,?,?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "REF3");
        stmt.setString(2, "22 YEARS EXPERIENCE");
        stmt.setString(3, "David");
        stmt.setString(4, "123456");
        stmt.setString(5, "3");
        stmt.setString(6, "LEAGUE1");
        stmt.executeUpdate();
        stmt.close();
//        //_________________________________________________________________
        sql = "INSERT INTO Referees(refereeID,qualification,userName,password,refNum) VALUES(?,?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "REF4");
        stmt.setString(2, "20 YEARS EXPERIENCE");
        stmt.setString(3, "Moshe3");
        stmt.setString(4, "123456");
        stmt.setString(5, "4");
        stmt.executeUpdate();
        stmt.close();

        dbc.disconnect(conn);
    }

    //============ assign referee to league ===========

    @Test
    public void assign_valid_referee_to_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        //(+)
        HashMap<String,String>  referee_details = dc.assign_referee_to_league("REF4","LEAGUE1");
        assertEquals("REF4",referee_details.get("refereeID"));
        assertEquals("LEAGUE1",referee_details.get("leagueID"));
    }

    @Test(expected = ObjectIDNotExistException.class)
    public void assign_invalid_referee_to_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        //(-)
        HashMap<String,String>  res = dc.assign_referee_to_league("REF7","LEAGUE1");
    }

    @Test(expected = ObjectIDNotExistException.class)
    public void assign_referee_to_invalid_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        HashMap<String,String>  res = dc.assign_referee_to_league("REF4","LEAGUE7");
    }

    @Test(expected = ScheduleRefereeFailed.class)
    public void assign_referee_with_league_to_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        HashMap<String,String>  res = dc.assign_referee_to_league("REF1","LEAGUE1");
    }

    //====== assign referee to game =========
    @Test
    public void assign_main_referee_to_game_valid() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        //(+)
        HashMap<String,String> res = dc.assign_referee_to_game("REF1","GAME1",1);
        assertEquals(res.get("game_id"),"GAME1");
        assertEquals(res.get("main_referee"),"REF1");
    }
    @Test
    public void assign_secondary_referee_to_game_valid() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        //(+)
        HashMap<String,String> res = dc.assign_referee_to_game("REF1","GAME3",2);
        assertEquals(res.get("game_id"),"GAME3");
        assertEquals(res.get("secondary_referee_1"),"REF1");
    }
    @Test
    public void assign_secondary2_referee() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        //(+)
        HashMap<String,String> res = dc.assign_referee_to_game("REF3","GAME4",2);
        assertEquals(res.get("game_id"),"GAME4");
        assertEquals(res.get("secondary_referee_2"),"REF3");
    }

    @Test(expected = ScheduleRefereeFailed.class)
    public void assign_secondary_referee_twise() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        HashMap<String,String> res = dc.assign_referee_to_game("REF1","GAME4",2);
    }

    @Test(expected = NullPointerException.class)
    public void assign_referee_without_league_to_game() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        dc.setCache(new HashMap<>());

        HashMap<String,String>  res = dc.assign_referee_to_game("REF4","GAME1",1);
    }

    @Test(expected = NullPointerException.class)
    public void assign_referee_to_game_without_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        HashMap<String,String>  res = dc.assign_referee_to_game("REF1","GAME2",1);
    }

    @Test(expected = NullPointerException.class)
    public void assign_without_league_referee_to_game_without_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        HashMap<String,String>  res = dc.assign_referee_to_game("REF4","GAME2",1);
    }


    @AfterClass
    public static void tearDown() throws Exception {
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "DELETE FROM Referees";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();

        dbc.disconnect(conn);
    }
}
