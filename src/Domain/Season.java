package Domain;

public class Season {
    int seasonID;
    String name;
    String startDate;
    String endDate;

    public Season(int seasonID, String name, String startDate, String endDate) {
        this.seasonID = seasonID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
