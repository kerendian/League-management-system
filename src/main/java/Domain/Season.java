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

}
