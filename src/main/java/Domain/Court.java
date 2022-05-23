package Domain;

public class Court
{
    String courtID;
    String name;
    int counter =3;

    public Court(String name) {
        this.name = name;
        this.courtID = "COURT"+counter++;
    }

    public String getName() {
        return name;
    }
}
