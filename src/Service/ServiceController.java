package Service;

import DataAccess.DAController;
import Domain.DomainController;
import Domain.DomainControllerInterface;
import Domain.UserStatus;
import Exceptions.WrongPasswordException;
import Exceptions.WrongUserNameException;


import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
public class ServiceController {
    private static final Logger logger = Logger.getLogger(ServiceController.class.getName());
    FileHandler fileHandler;
    DomainControllerInterface domainController;

    {

    }
    String userType_mem = null;

    //remember when using constructor in acceptance / integration tests to send new DomainController(DAController.getInstance())

    public ServiceController(DomainController domainController) {
        try
        {
            this.fileHandler = new FileHandler("status.log",true);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.domainController = domainController;
    }

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
            logger.log(Level.INFO,"user "+ userName + " logged in to the system as " + userType);

            return us;
        }
        else if (us == UserStatus.WrongPassword)
        {
            logger.log(Level.WARNING,"user "+ userName + " tried to log in to the system as " + userType + " with wrong password");
           throw new WrongPasswordException("Wrong Password");
        }
        else
        {
            logger.log(Level.WARNING,"user "+ userName + " tried to log in to the system as " + userType + " with wrong username or user type");
            throw new WrongUserNameException("There is no " + userType + " with the userName " + userName + " in the system");
        }


    }
//    TODO: remove if dont need
//    public String getUserType_mem(){ return user;}


//    public Status schedualeGame(String game_id, Date date, Time time, String court_id,String league_id){
//        domainController
//    }

    }

