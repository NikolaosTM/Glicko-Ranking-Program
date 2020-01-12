package topaloglou.mundy_niko_chess;

import java.util.*;

/**
 *
 * @author nikotopaloglou-mundy
 */
public class TopaloglouMundy_Niko_Chess {
    
    public static void main(String[] args) {
        
        Scanner r = new Scanner(System.in);
        
        System.out.print("Enter the filepath of your chessplayer file: ");
        // src/topaloglou/mundy_niko_chess/ChessPlayers.txt
        
        // /Users/nikotopaloglou-mundy/NetBeansProjects/Topaloglou-Mundy_Niko_Chess/src/topaloglou/mundy_niko_chess/ChessPlayers.txt
        
        String file = r.nextLine();
        Chess c = new Chess(file);
        
        boolean exit = false;
        while (exit == false){
            System.out.println("Select your choice by entering the corresponding number to your choice");
                System.out.println("\t1 - Load/Save file");
                System.out.println("\t2 - Add/Remove/Modify a player");
                System.out.println("\t3 - Find and print a player by Name/Rank");
                System.out.println("\t4 - Print a list of players sorted by Rank/Record/Last Game");
                System.out.println("\t5 - Input new match");
                System.out.println("\t0 - Exit program");
            
            int in = checkInput(5,r);
            
            switch (in) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    System.out.println("Would you like to:");
                        System.out.println("\t1 - Load file");
                        System.out.println("\t2 - Save file");
                        System.out.println("\t0 - Exit");
                    
                    in = checkInput(2,r);
                    
                    switch (in) {
                        case 0:
                            break;
                        case 1:
                            c.loadPlayerList(file);
                            break;
                        case 2:
                            c.savePlayerList(file);
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Would you like to:");
                        System.out.println("\t1 - Add a player");
                        System.out.println("\t2 - Remove a player");
                        System.out.println("\t3 - Upgrade an unranked player to a ranked player");
                        System.out.println("\t0 - Exit");
                    
                    in = checkInput(3,r);
                    
                    switch (in){
                        case 0:
                            break;
                        case 1:
                            System.out.println("Would you like to:");
                                System.out.println("\t1 - Add an unranked player");
                                System.out.println("\t2 - Add a ranked player");
                                System.out.println("\t0 - Exit");
                            
                            in = checkInput(2,r);
                            
                            switch (in){
                                case 0:
                                    break;
                                case 1:
                                    System.out.print("Enter the new player's name: ");
                                    c.addPlayer(r.nextLine());
                                    break;
                                case 2:
                                    System.out.print("Enter the new player's name: ");
                                    c.addRankedPlayer(r.nextLine());
                                    break;
                            }
                            break;
                        case 2:
                            System.out.print("Enter the name of the player you would like to remove: ");
                            c.deletePlayer(c.getPlayer(r.nextLine()));
                            break;
                        case 3:
                            System.out.print("Enter the name of the unranked player that you would like to upgrade: ");
                            c.addRankedPlayer(c.getPlayer(r.nextLine()));
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Would you like to:");
                        System.out.println("\t1 - Find and print player by name");
                        System.out.println("\t2 - Find and print ranked player by rank");
                        System.out.println("\t0 - Exit");
                        
                    in = checkInput(2,r);
                    
                    switch (in){
                        case 0:
                            break;
                        case 1:
                            System.out.print("Enter the player's name: ");
                            System.out.println(c.getPlayer(r.nextLine()));
                            break;
                        case 2:
                            System.out.print("Enter the ranked player's rank: ");
                            in = checkInput(c.rankedList.size(),r);
                            System.out.println(c.findPlayer(in));
                            break;
                        }
                    break;
                case 4:
                    System.out.println("Would you like to:");
                        System.out.println("\t1 - Print a list of ranked players sorted by rank");
                        System.out.println("\t2 - Print a list of players sorted by record (recent W/D/L)");
                        System.out.println("\t3 - Print a list of players by most recent games played");
                        System.out.println("\t0 - Exit");
                        
                    in = checkInput(3,r);
                    
                        switch (in){
                            case 0:
                                break;
                            case 1:
                                c.printRankedList();
                                break;
                            case 2:
                                c.winDrawLossList();
                                break;
                            case 3:
                                c.printRecentList();
                                break;
                        }
                    break;
                case 5:
                    System.out.println("Would you like to:");
                        System.out.println("\t1 - Enter a casual (unranked) match");
                        System.out.println("\t2 - Enter a ranked match between two ranked players");
                        System.out.println("\t0 - Exit");
                    
                    String p1;
                    String p2;
                    String date;
                    String result;
                    
                    in = checkInput(2,r);
                    
                    switch (in){
                        case 0:
                            break;
                        case 1:
                            System.out.println("Enter the names of");
                                System.out.print("\tPlayer 1: ");
                                p1 = r.nextLine();
                                System.out.print("\tPlayer 2: ");
                                p2 = r.nextLine();
                            
                            System.out.print("Enter the current date (month/year of format xx/xxxx): ");
                            date = r.nextLine();
                            
                            System.out.print("Enter the result for Player 1(W/D/L): ");
                            result = r.nextLine();
                            
                            c.match(c.getPlayer(p1), c.getPlayer(p2), date, result);
                            break;
                        case 2:
                            System.out.println("Enter the names of");
                                System.out.print("\tRanked Player 1: ");
                                p1 = r.nextLine();
                                System.out.print("\tRanked Player 2: ");
                                p2 = r.nextLine();
                            
                            System.out.print("Enter the current date (month/year of format xx/xxxx): ");
                            date = r.nextLine();
                            
                            System.out.print("Enter the result for Player 1(W/D/L): ");
                            result = r.nextLine();
                            
                            c.match(c.getRankedPlayer(p1),c.getRankedPlayer(p2),date,result);
                            break;
                        }
                    break;
            }
            
        }
    }
    
    public static int checkInput(int maxNum, Scanner r) {
        int output;
        try {
            output = Integer.parseInt(r.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(e);
            System.out.println("Invalid input, please try again.");
            output = checkInput(maxNum, r);
        }
        if (output > maxNum || output < 0) {
            System.out.println(("Make sure that your number is between 0 and " + maxNum + " inclusive, please try again."));
            output = checkInput(maxNum, r);
        }
        return output;
    }
    
}
