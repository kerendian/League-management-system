package UnitTest.Domain;

import Domain.Court;
import Domain.Season;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourtTest {
    @Test
    public void getCourtName()
    {
        Court court = new Court("court1");
        assertEquals("court1",court.getName());
    }

}