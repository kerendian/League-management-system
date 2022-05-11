package Domain;

import DataAccess.DAController;

public class DomainController {
    DAController loginDAController = DAController.getInstance();

    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus us = loginDAController.findUser(userName,password,userType);
        return us;

    }
}
