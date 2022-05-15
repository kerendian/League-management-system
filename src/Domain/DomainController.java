package Domain;

import DataAccess.DAController;
import Exceptions.SheduleRefereeFailed;
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


    public void assign_referee_to_game(String referee_id, String game_id){

        try {
            //checking the game exists
            HashMap<String, String> game_details = daController.findGame(game_id);
            //checking referee exists
            HashMap<String, String> referee_details = daController.findReferee(referee_id);

            if (referee_details.get("leagueID") != game_details)


        }catch(Exception e){

        System.out.println(e.getMessage());


        }
    }

    public void assign_referee_to_league(String referee_id,String league_id){

        try {

            //checking the league id exists and getting the league id

            HashMap<String,String>  league_details = daController.findLeague(league_id);


            //checking the referee id exists
            HashMap<String,String>  referee_details = daController.findReferee(referee_id);

            //checking the referee have no league

            if (referee_details.get("leagueID")==null) {

                // schedule league to referee
                daController.updateLeagueToReferee(referee_id, league_id);
            }

            else{
                throw new SheduleRefereeFailed("The referee is already schedule to another league");
            }
        }catch(Exception e){

            System.out.println(e.getMessage());


        }

    }


        game_placement(String date, int hour , String court, String league)

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
