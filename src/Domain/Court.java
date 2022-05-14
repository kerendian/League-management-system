package Domain;

public class Court {
    String courtID;
    String name;
    int counter =1;

    public Court(String name) {
        this.name = name;
        this.courtID = "Court"+counter++;
    }

    public String getCourtID() {
        return courtID;
    }

    public void setCourtID(String courtID) {
        this.courtID = courtID;
    }
}
