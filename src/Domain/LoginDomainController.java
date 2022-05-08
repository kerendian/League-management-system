package Domain;

import DataAccess.LoginDAController;

public class LoginDomainController {
    LoginDAController loginDAController = LoginDAController.getInstance();

    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus us = loginDAController.findUser(userName,password,userType);
        return us;

    }
}
