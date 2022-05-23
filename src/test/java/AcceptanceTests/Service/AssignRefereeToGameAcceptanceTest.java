

package AcceptanceTests.Service;

import DataAccess.DAController;
import DataAccess.DAControllerInterface;
import DataAccess.DBConnector;
import Domain.DomainController;
import Domain.UserStatus;
import Exceptions.ImportDataException;
import Exceptions.NullPointerException;
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

public class AssignRefereeToGameAcceptanceTest {

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

            sql = "DELETE FROM Teams";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
            sql = "DELETE FROM Games";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();

            sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID,leagueID) VALUES(?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "GAME1");
            stmt.setString(2, "TEAM1");
            stmt.setString(3, "TEAM2");
            stmt.setString(4, "LEAGUE1");
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
            sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID,leagueID) VALUES(?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "GAME3");
            stmt.setString(2, "TEAM1");
            stmt.setString(3, "TEAM2");
            stmt.setString(4, "LEAGUE1");
            stmt.executeUpdate();
            stmt.close();
//        //_________________________________________________________________
            sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID,leagueID,main_referee_ID,secondary_referee_ID1) VALUES(?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "GAME4");
            stmt.setString(2, "TEAM1");
            stmt.setString(3, "TEAM2");
            stmt.setString(4, "LEAGUE1");
            stmt.setString(5, "REF1");
            stmt.setString(6, "REF2");
            stmt.executeUpdate();
            stmt.close();
            //_________________________________________________________________
            sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID) VALUES(?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "GAME5");
            stmt.setString(2, "TEAM2");
            stmt.setString(3, "TEAM1");
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

            sql = "INSERT INTO Referees(refereeID,qualification,userName,password,refNum,leagueID) VALUES(?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "REF3");
            stmt.setString(2, "30 YEARS EXPERIENCE");
            stmt.setString(3, "David1");
            stmt.setString(4, "123456");
            stmt.setString(5, "21");
            stmt.setString(6, "LEAGUE1");
            stmt.executeUpdate();
            stmt.close();
            sql = "INSERT INTO Referees(refereeID,qualification,userName,password,refNum) VALUES(?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "REF4");
            stmt.setString(2, "30 YEARS EXPERIENCE");
            stmt.setString(3, "hen2");
            stmt.setString(4, "123456");
            stmt.setString(5, "221");
            stmt.executeUpdate();
            stmt.close();
            dbc.disconnect(conn);

        }

        //============ assign referee to league ===========

    //====== assign referee to game =========
    @Test
    public void assign_main_referee_to_game_valid() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        //(+)
        HashMap<String,String> res = sc.assign_referee_to_game("REF1","GAME1",1);
        assertEquals(res.get("game_id"),"GAME1");
        assertEquals(res.get("main_referee"),"REF1");
    }
    @Test
    public void assign_secondary_referee_to_game_valid() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        //(+)
        HashMap<String,String> res = sc.assign_referee_to_game("REF1","GAME3",2);
        assertEquals(res.get("game_id"),"GAME3");
        assertEquals(res.get("secondary_referee_1"),"REF1");
    }
    @Test
    public void assign_secondary2_referee() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        //(+)
        HashMap<String,String> res = sc.assign_referee_to_game("REF3","GAME4",2);
        assertEquals(res.get("game_id"),"GAME4");
        assertEquals(res.get("secondary_referee_2"),"REF3");
    }

    @Test(expected = ScheduleRefereeFailed.class)
    public void assign_secondary_referee_twise() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        HashMap<String,String> res = sc.assign_referee_to_game("REF1","GAME4",2);
    }

    @Test(expected = NullPointerException.class)
    public void assign_referee_without_league_to_game() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        HashMap<String,String>  res = sc.assign_referee_to_game("REF4","GAME1",1);
    }

    @Test(expected = NullPointerException.class)
    public void assign_referee_to_game_without_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        HashMap<String,String>  res = sc.assign_referee_to_game("REF1","GAME2",1);
    }

    @Test(expected = NullPointerException.class)
    public void assign_without_league_referee_to_game_without_league() throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        HashMap<String,String>  res = sc.assign_referee_to_game("REF4","GAME2",1);
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

            sql = "DELETE FROM Teams";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
            sql = "DELETE FROM Games";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();

            dbc.disconnect(conn);
        }


    }