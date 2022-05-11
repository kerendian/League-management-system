package Service;

import Domain.DomainController;
import Domain.UserStatus;
public class ServiceController {
    DomainController loginDomainController = new DomainController();
    String userType_mem = null;
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
                userType_mem = userType;
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
    public String getUserType_mem(){ return userType_mem;}


}
