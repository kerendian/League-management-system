package Domain;

import Service.ServiceController;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ServiceController la =new ServiceController();
        try {
            la.logIn("Yosi","123456","Referees");
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    }

}
