package Service;

import Domain.DomainController;
import Domain.DomainControllerInterface;
import Domain.UserStatus;
import Exceptions.*;
import Exceptions.NullPointerException;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;
public class ServiceController {
    private static final Logger logger = Logger.getLogger(ServiceController.class.getName());
    FileHandler fileHandler;
    DomainControllerInterface domainController;
    String userType_mem = null;

    //remember when using constructor in acceptance / integration tests to send new DomainController(DAController.getInstance())
    public ServiceController(DomainController domainController) {
        try
        {
            this.fileHandler = new FileHandler("status.log",true);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
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


    public HashMap<String,String> assign_referee_to_game(String referee_id,String game_id, int ref_type) throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {

        return domainController.assign_referee_to_game(referee_id,game_id,ref_type);
    }

    public HashMap<String, String> assign_referee_to_league(String referee_id, String league_id) throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        return domainController.assign_referee_to_league(referee_id,league_id);

    }

    ArrayList<HashMap<String,String>> games_placement(String date, int hour , String leagueID, String game_id) throws ObjectIDNotExistException, SQLException, ImportDataException, ParseException, InvalidDateException, ScheduleRefereeFailed, ScheduleGameFailed {
        return domainController.games_placement(date,hour, leagueID, game_id);
        }
    }


