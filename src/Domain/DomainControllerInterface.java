package Domain;

import java.util.HashMap;

public interface DomainControllerInterface
{
    //TODO:add all the signature of the functions from domain controller
    public UserStatus findUser(String userName, String password, String userType);
    public HashMap<String,String> findGame(String game_id);
    void games_placement(HashMap<String,String> game_details);
}
