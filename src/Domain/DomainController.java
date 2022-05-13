package Domain;

import DataAccess.DAController;

public class DomainController {
    DAController daController = DAController.getInstance();

    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus us = daController.findUser(userName,password,userType);
        return us;

    }
}
