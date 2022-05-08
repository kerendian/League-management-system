package Service;

import Domain.LoginDomainController;
import Domain.UserStatus;
public class LoginServiceController {
    LoginDomainController loginDomainController = new LoginDomainController();

    public void logIn(String userName, String password, String userType)
    {
        if (password.length() < 6)
        {
            System.out.println("Password must be at least 6 characters");
            return;
        }
        UserStatus us= loginDomainController.findUser(userName,password,userType);
        if (us != null) {
            if (us == UserStatus.Valid) {
                System.out.println("Login Successful");
            } else if (us == UserStatus.WrongPassword) {
                System.out.println("Wrong Password");
            } else {
                System.out.println("There is no " + userType + " with the userName " + userName + " in the system");
            }
        }
        else
        {
            System.out.println("Exception occurred");
        }
    }

}
