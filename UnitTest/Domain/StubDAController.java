package Domain;

import DataAccess.DAControllerInterface;

import java.util.HashMap;

public class StubDAController implements DAControllerInterface
{
    public UserStatus findUser(String userName, String password, String userType)
    {
        if (userName.equals("Alice") && userType.equals("Referees"))
        {
            if (password.equals("test1234"))
            {
                return UserStatus.Valid;
            }
            else
            {
                return UserStatus.WrongPassword;
            }
        }
        else
        {
            return UserStatus.WrongType;
        }
    }

    @Override
    public HashMap<String, String> findGame(String game_id) {
        return null;
    }

    @Override
    public HashMap<String, String> findLeague(String league_id) {
        return null;
    }

    @Override
    public HashMap<String, String> findCourt(String court_id) {
        return null;
    }

    @Override
    public void games_placement(HashMap<String, String> game_details) {

    }

    @Override
    public void referees_placement(HashMap<String, String> game_details) {

    }
}
