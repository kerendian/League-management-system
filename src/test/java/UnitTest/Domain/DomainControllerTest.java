package UnitTest.Domain;

import Domain.DomainController;
import Domain.UserStatus;
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
        dc = new DomainController(new StubDAController());

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
}