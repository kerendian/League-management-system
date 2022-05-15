package Service;

import Domain.DomainController;
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
//    public String getUserType_mem(){ return user;}


//    public Status schedualeGame(String game_id, String date, String  time, String court_id,String league_id){
//
//        //domainController.games_placement();
//    }



    public void assign_referee_to_game(String referee_id,String game_id){
        try{
        domainController.assign_referee_to_game(referee_id,game_id);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void assign_referee_to_league(String referee_id,String league_id){
        try{
            domainController.assign_referee_to_league(referee_id,league_id);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    }

