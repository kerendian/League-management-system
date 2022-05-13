package Service;

import Domain.DomainController;
import Domain.UserStatus;
import Exceptions.WrongPasswordException;
import Exceptions.WrongUserNameException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
public class ServiceController {
//    private static final Logger logger = Logger.getLogger(ServiceController.class.getName());


    DomainController domainController = new DomainController();
    String userType_mem = null;
    public UserStatus logIn(String userName, String password, String userType) throws Exception
    {
        if (password.length() < 6)
        {
            throw  new WrongPasswordException("Password must be at least 6 characters");
        }
        UserStatus us= domainController.findUser(userName,password,userType);

        if (us == UserStatus.Valid)
        {
//          System.out.println("Login Successful");
            userType_mem = userType;

            return us;
        }
        else if (us == UserStatus.WrongPassword)
        {
           throw new WrongPasswordException("Wrong Password");
        }
        else
        {
            throw new WrongUserNameException("There is no " + userType + " with the userName " + userName + " in the system");
        }


    }
//    TODO: remove if dont need
    public String getUserType_mem(){ return userType_mem;}


}
