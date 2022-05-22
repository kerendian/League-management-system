package Service;

import DataAccess.DAController;
import DataAccess.DAControllerInterface;
import DataAccess.DBConnector;
import Domain.DomainController;
import Exceptions.*;
import org.junit.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class GameScheduleTests {
    private static ServiceController sc;

    @BeforeClass
    public static void setUp() throws Exception
    {
        sc = new ServiceController(new DomainController(DAController.getInstance()));

        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "INSERT INTO Games(gameID,date,homeTeam_ID,externalTeam_ID,leagueID) VALUES(?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "GAME1");
        stmt.setString(2, "2022/08/02");
        stmt.setString(3, "TEAM1");
        stmt.setString(4, "TEAM2");
        stmt.setString(5, "LEAGUE1");
        stmt.executeUpdate();
        stmt.close();
        //_________________________________________________________________
        sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID) VALUES(?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "GAME2");
        stmt.setString(2, "TEAM2");
        stmt.setString(3, "TEAM3");
        stmt.executeUpdate();
        stmt.close();
        //_________________________________________________________________
        sql = "INSERT INTO Teams(teamID,team_courtID) VALUES(?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "TEAM2");
        stmt.setString(2, "COURT2");
        stmt.executeUpdate();
        stmt.close();
        //_________________________________________________________________
        sql = "INSERT INTO Teams(teamID,team_courtID) VALUES(?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "TEAM3");
        stmt.setString(2, "COURT3");
        stmt.executeUpdate();
        stmt.close();
        //_________________________________________________________________
        sql = "INSERT INTO Leagues(leagueID,seasonID,policyID) VALUES(?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "LEAGUE1");
        stmt.setString(2, "POLICY1");
        stmt.setString(3, "SEASON1");
        stmt.executeUpdate();
        stmt.close();
        //_________________________________________________________________
        sql = "INSERT INTO Leagues(leagueID,seasonID,policyID) VALUES(?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "LEAGUE2");
        stmt.setString(2, "POLICY2");
        stmt.setString(3, "SEASON1");
        stmt.executeUpdate();
        stmt.close();

        dbc.disconnect(conn);
    }
    //_________________________tests____________________________
    @Test
    public void games_placement_valid1() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        //(+) policy1
        ArrayList<HashMap<String, String>> res = sc.games_placement("2022/08/02",12,"LEAGUE1","GAME2");
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
        ArrayList<HashMap<String, String>> res = sc.games_placement("2021/08/02",12,"LEAGUE1","GAME2");
    }

    @Test(expected = ScheduleGameFailed.class)//policy1
    public void games_placement_team_has_game() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        //one of the teams of this game has game in the same day
        ArrayList<HashMap<String, String>> res = sc.games_placement("2022/08/02",12,"LEAGUE1","GAME1");
    }

    @Test(expected = ObjectIDNotExistException.class)//policy1
    public void games_placement_invalid_gameid() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        ArrayList<HashMap<String, String>> res = sc.games_placement("2022/08/02",12,"LEAGUE1","GAME7");
    }

    @Test(expected = ObjectIDNotExistException.class)//policy1
    public void games_placement_invalid_leagueid() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        ArrayList<HashMap<String, String>> res = sc.games_placement("2022/08/02",12,"LEAGUE7","GAME2");
    }
    @Test
    public void games_placement_valid2() throws InvalidDateException, ImportDataException, ParseException, ScheduleRefereeFailed, SQLException, ObjectIDNotExistException, ScheduleGameFailed {
        //(+) policy1
        ArrayList<HashMap<String, String>> res = sc.games_placement("2022/08/02",12,"LEAGUE2","GAME2");
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
        ArrayList<HashMap<String, String>> res = sc.games_placement("2022/08/02", 12, "LEAGUE2", "GAME5");
    }


    @AfterClass
    public static void tearDown() throws Exception
    {
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "DELETE FROM Games WHERE gameID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "GAME1");
        stmt.executeUpdate();
        stmt.close();
        //______________________________________________________
        sql = "DELETE FROM Games WHERE gameID = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "GAME2");
        stmt.executeUpdate();
        stmt.close();
        //______________________________________________________
        sql = "DELETE FROM Teams WHERE teamID = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "TEAM2");
        stmt.executeUpdate();
        stmt.close();
        //______________________________________________________
        sql = "DELETE FROM Teams WHERE teamID = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "TEAM3");
        stmt.executeUpdate();
        stmt.close();
        //______________________________________________________
        sql = "DELETE FROM Leagues WHERE leagueID = ?";
        stmt = conn.prepareStatement(sql);
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
