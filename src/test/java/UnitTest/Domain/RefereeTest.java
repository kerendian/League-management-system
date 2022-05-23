package UnitTest.Domain;

import Domain.Referee;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RefereeTest {


    @Test
    public void getRefNum() {
        Referee ref = new Referee("rafi","rafi123","123");
        assertEquals("123",ref.getRefNum());
    }

    @Test
    public void setRefNum() {
        Referee ref = new Referee("rafi","rafi123","123");
        ref.setRefNum("1234");
        assertEquals("1234",ref.getRefNum());

    }

    @Test
    public void getQualification() {
        Referee ref = new Referee("rafi","rafi123","123");
        ref.setQualification("test");
        assertEquals("test",ref.getQualification());
    }

    @Test
    public void testToString() {
        Referee ref = new Referee("rafi","rafi123","123");
        assertEquals("Referee{refereeID='REF5', leagueID='null', userName='rafi', password='rafi123'}",ref.toString());
    }
}