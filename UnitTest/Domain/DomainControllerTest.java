package Domain;

import Service.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DomainControllerTest {
private static DomainController dc;



    @BeforeClass
    public static void setUp() throws Exception
    {
        dc = new DomainController(new StubDAController());//domain controller

    }


    //============ login user ===========
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

    //============ assign referee to league ===========

    @Test
    public void assign_valid_referee_to_league(){
        Status  res = dc.assign_referee_to_league("REF1","LEAGUE1");
        assertEquals(Status.success,res);
    }

    @Test
    public void assign_invalid_referee_to_league(){
        Status  res = dc.assign_referee_to_league("REF2","LEAGUE1");
        assertEquals(Status.failure,res);
    }
    @Test
    public void assign_referee_to_invalid_league(){
        Status  res = dc.assign_referee_to_league("REF1","LEAGUE2");
        assertEquals(Status.failure,res);
    }

    @Test
    public void assign_invalid_referee_to_invalid_league(){
        Status  res = dc.assign_referee_to_league("REF2","LEAGUE2");
        assertEquals(Status.failure,res);
    }

    //====== assign referee to game =========

    //MISSING - (+) positive tes id not working
    //          (+) the check should check also the exception type , no?

    @Test
    public void assign_referee_without_league_to_game(){
        Status  res = dc.assign_referee_to_game("REF1","GAME1",1);
        assertEquals(Status.failure,res);
    }

    @Test
    public void assign_referee_to_game_without_league(){
        Status  res = dc.assign_referee_to_game("REF1","GAME2",1);
        assertEquals(Status.failure,res);
    }

    @Test
    public void assign_without_league_referee_to_game_without_league(){
        Status  res = dc.assign_referee_to_game("REF2","GAME2",1);
        assertEquals(Status.failure,res);
    }



}