package Domain;

import DataAccess.DAController;
import DataAccess.DAControllerInterface;
import DataAccess.DBConnector;
import Exceptions.*;
import Exceptions.NullPointerException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.annotations.AfterTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
public class FindLeagueIntegrationTests {
    //integrating find league from Domain controller with find league from DAController
    static DomainController dc;
    @BeforeClass
    public static void setUp() throws Exception
    {
        DAControllerInterface my_stub = new StabFindLeague();
        dc = new DomainController(my_stub);
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "INSERT INTO Leagues(leagueID,seasonID,policyID) VALUES(?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "LEAGUE1");
        stmt.setString(2, "SEASON1");
        stmt.setString(3, "POLICY1");
        stmt.executeUpdate();
        stmt.close();
        //_________________________________________________________________
        sql = "INSERT INTO Leagues(leagueID,seasonID,policyID) VALUES(?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "LEAGUE2");
        stmt.setString(2, "SEASON1");
        stmt.setString(3, "POLICY2");
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
    //============== schedule game tests =============

    @Test
    public void games_placement_valid1() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        //(+) policy1
        ArrayList<HashMap<String, String>> res = dc.games_placement("2022/08/02",12,"LEAGUE1","GAME2");
        System.out.println(res);
        assertEquals(1,res.size());
        assertEquals("2022/08/02",res.get(0).get("date"));
        assertEquals("12",res.get(0).get("hour"));
        assertEquals("LEAGUE1", res.get(0).get("league"));
        assertEquals("GAME2", res.get(0).get("game_id"));
        assertEquals("COURT2", res.get(0).get("court"));
    }

    @Test(expected = InvalidDateException.class)//policy1
    public void games_placement_invalid_date() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        //date expired
        ArrayList<HashMap<String, String>> res = dc.games_placement("2021/08/02",12,"LEAGUE1","GAME2");
    }

    @Test(expected = ScheduleGameFailed.class)//policy1
    public void games_placement_team_has_game() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        //one of the teams of this game has game in the same day
        ArrayList<HashMap<String, String>> res = dc.games_placement("2022/08/02",12,"LEAGUE1","GAME1");
    }

    @Test(expected = ObjectIDNotExistException.class)//policy1
    public void games_placement_invalid_gameid() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        ArrayList<HashMap<String, String>> res = dc.games_placement("2022/08/02",12,"LEAGUE1","GAME7");
    }

    @Test(expected = ObjectIDNotExistException.class)//policy1
    public void games_placement_invalid_leagueid() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        ArrayList<HashMap<String, String>> res = dc.games_placement("2022/08/02",12,"LEAGUE7","GAME2");
    }
    @Test
    public void games_placement_valid2() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        //(+) policy1
        ArrayList<HashMap<String, String>> res = dc.games_placement("2022/08/02",12,"LEAGUE2","GAME2");
        assertEquals(2,res.size());
        assertEquals("2022/08/02",res.get(0).get("date"));
        assertEquals("12",res.get(0).get("hour"));
        assertEquals("LEAGUE2", res.get(0).get("league"));
        assertEquals("GAME2", res.get(0).get("game_id"));
        assertEquals("COURT2", res.get(0).get("court"));
        //game2
        assertEquals("2022/08/03",res.get(1).get("date"));
        assertEquals("12",res.get(1).get("hour"));
        assertEquals("LEAGUE2", res.get(1).get("league"));
        assertThat("GAME2", not(res.get(1).get("game_id")));
        assertEquals("COURT3", res.get(1).get("court"));
    }

    @Test(expected = ScheduleGameFailed.class)
    public void games_placement_has_game() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        //policy 2 , has game
        ArrayList<HashMap<String, String>> res = dc.games_placement("2022/08/02", 12, "LEAGUE2", "GAME5");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "DELETE FROM Leagues WHERE leagueID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "LEAGUE1");
        stmt.executeUpdate();
        stmt.close();
        //______________________________________________________
        sql = "DELETE FROM Leagues WHERE leagueID = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "LEAGUE2");
        stmt.executeUpdate();
        stmt.close();

        dbc.disconnect(conn);

    }
}
