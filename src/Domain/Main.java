package Domain;

import DataAccess.DBConnector;
import DataAccess.LoginDAController;
import Service.LoginServiceController;

public class Main {
    public static void main(String[] args) {
        LoginServiceController  la =new LoginServiceController();
        la.logIn("Amen","123456","Referees");
    }

}
