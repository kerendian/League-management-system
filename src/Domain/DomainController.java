package Domain;

import DataAccess.DAController;
import Service.Status;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

public class DomainController {
    DAController daController = DAController.getInstance();

    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus us = daController.findUser(userName,password,userType);
        return us;

    }

    public HashMap<String,String> findGame(String game_id){
        HashMap<String,String> game_row = daController.findGame(game_id);
        return game_row;
    }

    public void games_placement(HashMap<String,String> game_details){
        daController.games_placement(game_details);
    }

//    public Status schedualeGame(String game_id, Date date, Time time, String court_id, String league_id){
//
//        //trying importing the specific game from db
//        try {
//            Game my_game = daController.
//        }
//        catch(){
//
//        }
//    }
}
