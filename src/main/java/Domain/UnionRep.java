package Domain;

public class UnionRep extends SignedUser {
    String repID;
    String UnionNum;
    int counter=2;

    public UnionRep(String userName, String password) {
        super(userName, password);
        repID = "REP"+ counter++;
    }

    public String getRepID() {
        return repID;
    }
}
