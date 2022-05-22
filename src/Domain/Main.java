package Domain;

import DataAccess.DAController;
import DataAccess.DBConnector;
import Exceptions.InvalidDateException;
import Service.ServiceController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws ParseException, InvalidDateException, SQLException {
//        ServiceController la =new ServiceController(new DomainController(DAController.getInstance()));
//        try {
//            la.logIn("Yosi","123456","Referees");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        DomainController dc = new DomainController();
//        HashMap<String,String>  game_row =  dc.findGame("GAME2");
////        for (String name: game_row.keySet()) {
////            String key = name.toString();
////            if (game_row.get(name) != null) {
////                String value = game_row.get(name).toString();
////                System.out.println(key + " " + value);
////            }
////        }
////        game_row.put("date", "12/01/22");
////        dc.games_placement(game_row);
////
////        game_row =  dc.findGame("GAME2");
//        for (String name: game_row.keySet()) {
//            String key = name.toString();
//            if (game_row.get(name) != null) {
//                String value = game_row.get(name).toString();
//                System.out.println(value.getClass());
//                System.out.println(key + " " + value);
//            }
//        }


        DomainController dc = new DomainController(DAController.getInstance());
        DBConnector dbc = DBConnector.getInstance();
        Connection conn = dbc.connect();
        String sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID,leagueID) VALUES(?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "GAME1");
        stmt.setString(2, "TEAM1");
        stmt.setString(3, "TEAM2");
        stmt.setString(4, "LEAGUE1");
        stmt.executeUpdate();
        stmt.close();
        sql = "INSERT INTO Games(gameID,homeTeam_ID,externalTeam_ID,leagueID,main_referee_ID,secondary_referee_ID1) VALUES(?,?,?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "GAME4");
        stmt.setString(2, "TEAM1");
        stmt.setString(3, "TEAM2");
        stmt.setString(4, "LEAGUE1");
        stmt.setString(5, "REF1");
        stmt.setString(6, "REF2");
        stmt.executeUpdate();
        stmt.close();


    }

}
