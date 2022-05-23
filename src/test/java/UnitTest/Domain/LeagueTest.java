package UnitTest.Domain;

import Domain.League;
import Domain.Season;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeagueTest {

    @Test
    public void getSeasonID()
    {
        League league = new League();
        assertEquals("SEASON11",league.getSeasonID());
    }
}