package Domain;

public class Team {
    int counter=5;
    String teamID;
    String courtID;

    public Team(int counter) {
        this.teamID = "TEAM"+counter++;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getCourtID() {
        return courtID;
    }

    public void setCourtID(String courtID) {
        this.courtID = courtID;
    }
}
