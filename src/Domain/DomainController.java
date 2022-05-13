package Domain;

import DataAccess.DAController;
import Service.Status;

import java.sql.Time;
import java.util.Date;

public class DomainController {
    DAController daController = DAController.getInstance();

    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus us = daController.findUser(userName,password,userType);
        return us;

    }

    public Status schedualeGame(String game_id, Date date, Time time, String court_id, String league_id){

        //trying importing the specific game from db
        try {
            Game my_game = daController.
        }
        catch(){

        }
    }
}
