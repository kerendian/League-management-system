package Domain;

import DataAccess.DAControllerInterface;
import Exceptions.*;
import Exceptions.NullPointerException;
import Service.Status;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static Service.Status.failure;
import static Service.Status.success;

public class DomainController implements DomainControllerInterface {
    DAControllerInterface daController;
    HashMap<String,Object> cache=  new HashMap<>();

    //remember when using constructor in acceptance / integration tests to send DAController.getInstance()
    public DomainController(DAControllerInterface daController) {

        this.daController = daController;
        //HashMap<String,Object> cache = new HashMap<>();
    }

    public HashMap<String, Object> getCache() {
        return cache;
    }

    public void setCache(HashMap<String, Object> cache) {
        this.cache = cache;
    }

    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus us = daController.findUser(userName,password,userType);
        return us;

    }
    public static String getNextDate(String  curDate) throws ParseException, InvalidDateException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = format.parse(curDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return format.format(calendar.getTime());
    }
    public boolean date_isvalid(String date) throws InvalidDateException, ParseException {
        if (new SimpleDateFormat("yyyy/MM/dd").parse(date).before(new Date())) {
            throw new InvalidDateException("this date expired");
        }
        return true;
    }


    public ArrayList<HashMap<String,String>> games_placement(String date, int hour , String leagueID, String game_id) throws ObjectIDNotExistException, SQLException, ImportDataException, ParseException, InvalidDateException, ScheduleRefereeFailed, ScheduleGameFailed {
        ArrayList<HashMap<String,String>> array_to_return = new ArrayList<HashMap<String,String>>();
        //checking if the game_id in the local memory
        Game origin_game =null;
        if(cache.containsKey(game_id)) {
            origin_game = (Game) cache.get(game_id);
        }
        Game curr_game = origin_game;
        //if not- load the game details from db
        if (curr_game==null) {
            //create game object if not exsists
                HashMap<String,String> game_details = daController.findGame(game_id);
                curr_game = new Game(game_details.get("home_team"),game_details.get("external_team"));
                curr_game.setGame_id(game_id);
        }
        //updating basic details
        if(date_isvalid(date)) {
            curr_game.setDate(date);
        }
        curr_game.setHour(hour);
        curr_game.setLeagueID(leagueID);

        //try {
            //find teams and courts
            HashMap<String, String> home_team_details = daController.findTeam(curr_game.getHome_team_ID());
            HashMap<String, String> external_team_details = daController.findTeam(curr_game.getExternal_team_ID());
            HashMap<String, String> league_details = daController.findLeague(leagueID);
            if(daController.check_game_date_validation(home_team_details.get("team_id"), date) && daController.check_game_date_validation(external_team_details.get("team_id"), date)){
                if(league_details.get("policy_id").equals("POLICY1")){
                    curr_game.game_placement(date,hour,leagueID,league_details.get("policy_id"), home_team_details.get("court_id"), external_team_details.get("court_id"));
                    cache.put(curr_game.getGame_id(),curr_game);
                    //updating the details in the db
                    HashMap<String, String> game_details = curr_game.convertToHash();
                    daController.games_placement(game_details);
                    array_to_return.add(game_details);
                    return array_to_return;
                }
                else{
                    String date_game2 = getNextDate(date);
                    if(daController.check_game_date_validation(home_team_details.get("team_id"), date_game2) && daController.check_game_date_validation(external_team_details.get("team_id"), date_game2)){
                        Game game2 = curr_game.game_placement(date,hour,leagueID,league_details.get("policy_id"), home_team_details.get("court_id"), external_team_details.get("court_id"));
                        cache.put(game2.getGame_id(),game2);
                        cache.put(curr_game.getGame_id(),curr_game);
                        //updating the details in the db
                        HashMap<String, String> game_details = curr_game.convertToHash();
                        HashMap<String, String> game_details2 = game2.convertToHash();
                        daController.games_placement(game_details);
                        daController.games_placement(game_details2);
                        array_to_return.add(game_details);
                        array_to_return.add(game_details2);
                        return array_to_return;
                    }
                    else{
                        throw new ScheduleGameFailed("one of the teams in this game has already game at this date");
                    }
                }
            }
            else{
                throw new ScheduleGameFailed("one of the teams in this game has already game at this date");
            }
          //  return array_to_return;
    }


    public HashMap<String, String> assign_referee_to_game(String referee_id, String game_id,int type) throws ObjectIDNotExistException, SQLException, ImportDataException, NullPointerException, ScheduleRefereeFailed {
            //check if the game exists in the cache
            Game curr_game =null;
            if(cache.containsKey(game_id)) {
                 curr_game = (Game) cache.get(game_id);
            }
            //if not - import game from db
            if (curr_game == null) {
                HashMap<String, String> game_details = daController.findGame(game_id);
                curr_game = new Game(game_details.get("home_team"), game_details.get("external_team"));
                curr_game.setGame_id(game_id);
                curr_game.setDate(game_details.get("date"));
                String hour_detail = game_details.get("hour");
                if (hour_detail !=null ){
                curr_game.setHour(Integer.parseInt(game_details.get("hour")));
                }
                curr_game.setCourtID(game_details.get("court"));
                curr_game.setLeagueID(game_details.get("league"));
                curr_game.setMain_referee_ID(game_details.get("main_referee"));
                curr_game.setSecondary_referee_ID1(game_details.get("secondary_referee_1"));
                curr_game.setSecondary_referee_ID2(game_details.get("secondary_referee_2"));
            }

            //check if the referee exists in the cache
            Referee curr_referee =null;
            if(cache.containsKey(referee_id)) {
                curr_referee = (Referee) cache.get(referee_id);
            }
            if (curr_referee == null) {
                //checking the if the referee id exists in memory
                HashMap<String, String> referee_details = daController.findReferee(referee_id);
                curr_referee = new Referee(referee_details.get("username"), referee_details.get("password"), referee_details.get("refNum"));
                curr_referee.setQualification(referee_details.get("qualification"));
                curr_referee.setLeagueID(referee_details.get("leagueID"));
                curr_referee.setRefereeID(referee_id);
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

            // if the leagues are not equal
            if (!curr_referee.getLeagueID().equals(curr_game.getLeagueID())) {
                throw new ScheduleRefereeFailed("the chosen referee and the chosen game are not belong to the same league");
            }
            if(curr_referee.getRefereeID().equals(curr_game.getMain_referee_ID()) || curr_referee.getRefereeID().equals(curr_game.getSecondary_referee_ID1()) || curr_referee.getRefereeID().equals(curr_game.getSecondary_referee_ID2())){
                throw new ScheduleRefereeFailed("the chosen referee already schedual to this game");
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
                        HashMap<String,String> game_details = curr_game.convertToHash();
                        Status status_returned = daController.updateRefereesToGame(game_details);
                        if(status_returned==success){
                            return game_details;
                        }
                        else{
                            throw new ScheduleRefereeFailed("the status that returned is failure");
                        }

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
                            HashMap<String,String> game_details = curr_game.convertToHash();
                            Status status_returned = daController.updateRefereesToGame(game_details);
                            if(status_returned==success){
                                return game_details;
                            }
                            else{
                                throw new ScheduleRefereeFailed("the status that returned is failure");
                            }
                        }

                    } else {

                        curr_game.setSecondary_referee_ID1(referee_id);
                        cache.put(game_id, curr_game);
                        HashMap<String,String> game_details = curr_game.convertToHash();
                        Status status_returned = daController.updateRefereesToGame(game_details);
                        if(status_returned==success){
                            return game_details;
                        }
                        else{
                            throw new ScheduleRefereeFailed("the status that returned is failure");
                        }
                    }
            }
            return null;
    }
        public HashMap<String,String> assign_referee_to_league(String referee_id,String league_id) throws ObjectIDNotExistException, SQLException, ImportDataException, ScheduleRefereeFailed {

            //try {
            //checking the if the league id exists in memory
            HashMap<String, String> league_details = daController.findLeague(league_id);
            Referee curr_referee = null;
            //checking the league id exists in cache
            if (cache.containsKey("referee_id")) {
                curr_referee = (Referee) cache.get(referee_id);
            }

            if (curr_referee == null) {
                //checking the if the referee id exists in memory
                HashMap<String, String> referee_details = daController.findReferee(referee_id);
                curr_referee = new Referee(referee_details.get("username"), referee_details.get("password"), referee_details.get("refNum"));
                curr_referee.setRefereeID(referee_details.get("refereeID"));
                curr_referee.setQualification(referee_details.get("qualification"));
                curr_referee.setLeagueID(referee_details.get("leagueID"));
                cache.put(curr_referee.getRefereeID(), curr_referee);

            }

            //checking the referee have no league
            if (curr_referee.getLeagueID() == null || curr_referee.getLeagueID().equals("NULL") || curr_referee.getLeagueID().equals("")) {
                curr_referee.setLeagueID(league_id);
                Status status_returned = daController.updateLeagueToReferee(referee_id, league_id);
                if (status_returned == success) {
                    cache.put(curr_referee.getRefereeID(), curr_referee);
                    return curr_referee.get_referee_details();
                }
                else {
                    throw new ScheduleRefereeFailed("the status that returned is failure");
                }
            }
            else {
                throw new ScheduleRefereeFailed("The referee is already schedule to another league");
            }
    }
}
