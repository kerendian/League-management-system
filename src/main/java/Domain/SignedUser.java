package Domain;

public abstract class SignedUser {
    String userName;
    String password;

    public SignedUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
