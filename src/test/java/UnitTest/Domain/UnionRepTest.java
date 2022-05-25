package UnitTest.Domain;

import Domain.UnionRep;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnionRepTest {

    @Test
    public void getRepID() {
        UnionRep unionRep = new UnionRep("Alice","Pass1234");
        UnionRep unionRep1 = new UnionRep("Bob","Pass1234");
        assertEquals("REP2",unionRep.getRepID());
        assertEquals("REP3",unionRep1.getRepID());

    }
}