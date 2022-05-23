package UnitTest.Domain;

import Domain.Season;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeasonTest
{

    @Test
    public void getSeasonID()
    {
        Season season = new Season(2020);
        assertEquals("SEASON2",season.getSeasonID());
    }
    @Test
    public void setSeasonID()
    {
        Season season = new Season(2020);
        season.setSeasonID("SEASON3");
        assertEquals("SEASON3",season.getSeasonID());
    }
}