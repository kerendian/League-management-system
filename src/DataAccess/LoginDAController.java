package DataAccess;

import Domain.UserStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginDAController {
private static LoginDAController instance = new LoginDAController();
DBConnector dbc = DBConnector.getInstance();
private LoginDAController(){}

public static LoginDAController getInstance(){return  instance;};
    public UserStatus findUser(String userName, String password, String userType)
    {
        UserStatus  us = null;
        try {
            String sql = "SELECT * FROM "+ userType + " WHERE userName = '" + userName + "';" ;
            Connection conn = dbc.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                String passFromDB = rs.getString("password");
                if (passFromDB.equals(password))
                {
                    us = UserStatus.Valid;
                }
                else
                {
                    us = UserStatus.WrongPassword;
                }
            }
            else
            {
                us = UserStatus.WrongType;
            }
            rs.close();
            stmt.close();
            dbc.disconnect(conn);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    return us;
    }
}
