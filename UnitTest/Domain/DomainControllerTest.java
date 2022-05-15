package Domain;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DomainControllerTest {
private DomainController dc;
    @BeforeClass
    public void setUp() throws Exception
    {
        dc = new DomainController(new StubDAController());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findUser(String userName, String password, String userType)
    {
//        UserStatus res = dc.findUser()
    }
}