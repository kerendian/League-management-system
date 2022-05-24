package Service;
import Domain.DomainController;
import Domain.DomainControllerInterface;
import Domain.UserStatus;
import Exceptions.*;
import Exceptions.NullPointerException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceController {
    DomainControllerInterface domainController;
    String userType_mem = null;

    public ServiceController(DomainController domainController) {
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

    public HashMap<String,String> assign_referee_to_game(String referee_id,String game_id, int ref_type) throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException, NullPointerException {
        return domainController.assign_referee_to_game(referee_id,game_id,ref_type);
    }

    public HashMap<String, String> assign_referee_to_league(String referee_id, String league_id) throws ObjectIDNotExistException, SQLException, ScheduleRefereeFailed, ImportDataException {
        return domainController.assign_referee_to_league(referee_id,league_id);

    }

    public ArrayList<HashMap<String,String>> games_placement(String date, int hour, String leagueID, String game_id) throws ObjectIDNotExistException, SQLException, ImportDataException, ParseException, InvalidDateException, ScheduleRefereeFailed, ScheduleGameFailed {
        return domainController.games_placement(date,hour, leagueID, game_id);
    }
}


