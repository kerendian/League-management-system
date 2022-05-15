package Domain;

import DataAccess.DAControllerInterface;
import Exceptions.NullPointerException;
import Exceptions.ScheduleRefereeFailed;
import Service.Status;

import java.util.HashMap;

import static Service.Status.failure;
import static Service.Status.success;

public class DomainController implements DomainControllerInterface {
    DAControllerInterface daController;
    HashMap<String,Object> cache;

    //remember when using constructor in acceptance / integration tests to send DAController.getInstance()
    public DomainController(DAControllerInterface daController) {

        this.daController = daController;
        HashMap<String,Object> cache=new HashMap<>();
    }


    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus us = daController.findUser(userName,password,userType);
        return us;

    }

//    public HashMap<String,String> findGame(String game_id){
//        HashMap<String,String> game_row = daController.findGame(game_id);
//        return game_row;
//    }

    public Status games_placement(String date, int hour , String leagueID,String game_id){

        //checking if the game_id in the local memory
        Game curr_game = (Game) cache.get("game_id");
        //if not- load the game details from db
        if (curr_game==null) {
            //create game object if not exsists
            try{
                HashMap<String,String> game_details = daController.findGame(game_id);
                curr_game = new Game(game_details.get("home_team"),game_details.get("external_team"));
                curr_game.setGame_id(game_id);

            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
        //updating basic details
        curr_game.setDate(date);
        curr_game.setHour(hour);
        curr_game.setLeagueID(leagueID);

        try {
            //find teams and courts
            HashMap<String, String> home_team_details = daController.findTeam(curr_game.getHome_team_ID());
            HashMap<String, String> external_team_details = daController.findTeam(curr_game.getExternal_team_ID());
            HashMap<String, String> league_details = daController.findLeague(leagueID);
            curr_game.game_placement(date,hour,leagueID,league_details.get("policy_id"), home_team_details.get("court_id"), external_team_details.get("court_id"));

            cache.put(curr_game.getGame_id(),curr_game);

            //updating the details in the db
            daController.games_placement(curr_game.convertToHash());
            return success;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return failure;
        }

    }


    public Status assign_referee_to_game(String referee_id, String game_id,int type) {


        try {

            //check if the game exists in the cache
            Game curr_game = (Game) cache.get(game_id);
            //if not - import game from db
            if (curr_game == null) {
                HashMap<String, String> game_details = daController.findGame(game_id);
                curr_game = new Game(game_details.get("home_team"), game_details.get("external_team"));
                curr_game.setGame_id(game_id);
                curr_game.setDate(game_details.get("date"));
                curr_game.setHour(Integer.parseInt(game_details.get("hour")));
                curr_game.setCourtID(game_details.get("court"));
                curr_game.setLeagueID(game_details.get("league"));
                curr_game.setMain_referee_ID(game_details.get("main_referee"));
                curr_game.setSecondary_referee_ID1(game_details.get("secondary_referee_1"));
                curr_game.setSecondary_referee_ID2(game_details.get("secondary_referee_2"));

            }

            //check if the referee exists in the cache
            Referee curr_referee = (Referee) cache.get(referee_id);

            if (curr_referee == null) {
                //checking the if the referee id exists in memory
                HashMap<String, String> referee_details = daController.findReferee(referee_id);
                curr_referee = new Referee(referee_details.get("username"), referee_details.get("password"), referee_details.get("refNum"));
                curr_referee.setQualification(referee_details.get("qualification"));
                curr_referee.setLeagueID(referee_details.get("leagueID"));
                cache.put(curr_referee.getRefereeID(), curr_referee);
            }

            //CHECKS:

            //if the game has no league raise error
            if (curr_game.getLeagueID() == null || curr_game.getLeagueID().equals("NULL") || curr_game.getLeagueID().equals("")) {
                throw new NullPointerException("the game is not schedule to any league");

            }
            //if the referee has no league raise error
            if (curr_referee.getLeagueID() == null || curr_referee.getLeagueID().equals("NULL") || curr_referee.getLeagueID().equals("")) {
                throw new NullPointerException("the referee is not schedule to any league");

            }

            // if the leagues are equal else error
            if (!curr_referee.getLeagueID().equals(curr_game.getLeagueID())) {
                throw new ScheduleRefereeFailed("the chosen referee and the chosen game are not belong to the same league");

            }
            // if the type is not available
            switch (type) {
                case 1:
                    if (curr_game.getMain_referee_ID() != null && !(curr_game.getMain_referee_ID().equals("NULL")) && !(curr_game.getMain_referee_ID().equals(""))) {
                        throw new ScheduleRefereeFailed("the chosen game is already has a main referee");

                    } else {
                        //main referee
                        curr_game.setMain_referee_ID(referee_id);
                        cache.put(game_id, curr_game);
                        return daController.updateRefereesToGame(curr_game.convertToHash());

                    }

                case 2:
                    //if secondary1 is not available
                    if (curr_game.getSecondary_referee_ID1() != null && !(curr_game.getSecondary_referee_ID1().equals("NULL")) && !(curr_game.getSecondary_referee_ID1().equals(""))) {
                        //no available schedule
                        if (curr_game.getSecondary_referee_ID2() != null && !(curr_game.getSecondary_referee_ID2().equals("NULL")) && !(curr_game.getSecondary_referee_ID2().equals(""))) {
                            throw new ScheduleRefereeFailed("the chosen game is already has secondary referees");
                        }
                        //secondary referee2
                        else {
                            curr_game.setSecondary_referee_ID2(referee_id);
                            cache.put(game_id, curr_game);
                            return daController.updateRefereesToGame(curr_game.convertToHash());
                        }

                    } else {

                        curr_game.setSecondary_referee_ID1(referee_id);
                        cache.put(game_id, curr_game);
                        return daController.updateRefereesToGame(curr_game.convertToHash());

                    }



            }

            //update the game object, update cache and db.


        } catch (Exception e) {

            System.out.println(e.getMessage());
            return failure;


        }
        return failure;
    }

        public Status assign_referee_to_league(String referee_id,String league_id){

        try {

            //checking the if the league id exists in memory
            HashMap<String,String>  league_details = daController.findLeague(league_id);

            //checking the league id exists in cache
            Referee curr_referee = (Referee) cache.get(referee_id);

            if (curr_referee==null){
                //checking the if the referee id exists in memory
                HashMap<String,String>  referee_details = daController.findReferee(referee_id);
                curr_referee = new Referee(referee_details.get("username"),referee_details.get("password"),referee_details.get("refNum"));
                curr_referee.setQualification(referee_details.get("qualification"));
                curr_referee.setLeagueID(referee_details.get("leagueID"));
                cache.put(curr_referee.getRefereeID(),curr_referee);

            }

            //checking the referee have no league
            if (curr_referee.getLeagueID() ==null||curr_referee.getLeagueID().equals("NULL")||curr_referee.getLeagueID().equals("") ){
                curr_referee.setLeagueID(league_id);
                daController.updateLeagueToReferee(referee_id, league_id);
                return success;
            }

            else{
                throw new ScheduleRefereeFailed("The referee is already schedule to another league");

            }
        }catch(Exception e){

            System.out.println(e.getMessage());
            return failure;


        }

    }


}
