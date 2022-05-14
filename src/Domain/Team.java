package Domain;

public class Team {
    int counter=5;
    String teamID;

    public Team(int counter) {
        this.teamID = "TEAM"+counter++;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

}
