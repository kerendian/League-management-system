package Domain;

public class Season {
    String seasonID;
    int year;
    int counter=2;

    public Season(int year_s) {
        this.seasonID = "SEASON"+counter++;

    }

    public String getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
