package topaloglou.mundy_niko_chess;

import java.util.*;
import java.io.*;

public final class Chess {
    
    ArrayList<Player> playerList = new ArrayList<>();
    ArrayList<Player> rankedList = new ArrayList<>();
    final double P = (15.9)/(Math.pow(Math.PI, 2)*Math.pow(400, 2));
    final double Q = Math.log(10)/400;
    
    public Chess(String fileName){
        loadPlayerList(fileName);
    }
    
    
    /**
     * Sorts rankedList based on RankedPlayer rating (using bubble sort) and assigns ranks accordingly
     */
    public void bubbleRank(){
        int n = rankedList.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j <n-i-1; j++){
                if ( ((RankedPlayer)rankedList.get(j)).rating > ((RankedPlayer)rankedList.get(j+1)).rating){
                    Player temp = rankedList.get(j);
                    
                    rankedList.add(j, rankedList.get(j+1));
                    rankedList.remove(j+1);
                    rankedList.remove(j+1);                    
                    rankedList.add(j+1, temp);
                }
            }
        }
        for (int i = n-1; i >= 0; i--){
            ((RankedPlayer)rankedList.get(i)).rank = n-i;
        }
        
        //#1 ranked player is assigned rank 0 for some reason ???
        //this is a crappy solution but is backup plan
        //((RankedPlayer)rankedList.get(n-1)).rank = 1;
    }
    
    
    /**
     * Sorts playerList based on the most recent games played by each player
     */
    public void bubbleRecent(){
        int n = playerList.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j <n-i-1; j++){
                if ((monthsSince(playerList.get(j).lastGame,playerList.get(j + 1).lastGame) < 0)){
                    Player temp = playerList.get(j);
                    playerList.add(j, playerList.get(j+1));
                    playerList.remove(j+1);
                    playerList.add(j + 1, temp);
                    playerList.remove(j+2);
                }
            }
        }
    }
    
    
    /**
     *calculates the months between last game and the current date
     * @param lastGame
     * @param date
     * @return 
     */
    public int monthsSince(String lastGame, String date){
        
        // if the player has not yet played a game, it is necessary to set the lastGame = 0 so that ratings deviation (+/- 350) is not adjusted
        if(lastGame.equals("")||date.equals("")){
            return 0;
        } else {
        
            int months = Integer.parseInt(lastGame.split("/")[0]) 
                    - Integer.parseInt(date.split("/")[0]);
        
            int years = Integer.parseInt(lastGame.split("/")[1]) 
                    - Integer.parseInt(date.split("/")[1]);
        
        return years*12 + months;
        }
    }
    
    
    /**
     * loads info from a .txt file and sorts data into players and ranked players
     * @param fileName 
     */
    public void loadPlayerList(String fileName) {
        try {
            Scanner read = new Scanner(new FileReader(fileName));
            String line;
            while(read.hasNext()){
                line = read.nextLine();
                String[] arr = line.split(",");
                if (arr.length == 6) {
                    Player p = new RankedPlayer(arr[0], arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
                    playerList.add(p);
                    rankedList.add(p);
                }else{
                    Player p = new Player(arr[0], arr[1], arr[2]);
                    playerList.add(p);
                }
            }
            bubbleRank();
            read.close();
            System.out.println("File loaded successfully");
        }catch (IOException iox) {
            System.out.println("Failed to read file");
        }
    }
   
    
    /**
     * saves data into a .txt file
     * @param fileName 
     */
    public void savePlayerList(String fileName){
        try{
            BufferedWriter write = new BufferedWriter(new FileWriter(fileName, false));
            for (Player p : playerList) {
                if (p instanceof RankedPlayer) {
                    write.write(p.name + "," + p.record + "," +  p.lastGame + "," +
                    ((RankedPlayer) p).rating + "," + ((RankedPlayer) p).ratingsDeviation + "," + 
                    ((RankedPlayer) p).rank + "\n");
                }else {
                    write.write(p.name + "," + p.record + "," + p.lastGame + "\n");
                }
            }
            write.close();
            System.out.println("File saved successfully");
        }catch (IOException iox) {
            System.out.println("Problem writing " + fileName);
        }
    }
    
    
    /**
     * gets a player using his name
     * @param name
     * @return Player
     */
    public Player getPlayer(String name){
        for (Player p : playerList) {
            if (p.name.equals(name)){
                return p;
            }
        }
        System.out.println("Player not found.");
        return null;
    }
    
    
    /**
     * gets a player using his name
     * @param name
     * @return Player
     */
    public RankedPlayer getRankedPlayer(String name){
        for (Player p : rankedList) {
            if (p.name.equals(name)){
                return (RankedPlayer)p;
            }
        }
        System.out.println("Player not found.");
        return null;
    }
    
    
    /**
     * finds the RankedPlayer that holds the certain rank
     * @param rank
     * @return RankedPlayer
     */
    public RankedPlayer findPlayer(int rank){
        for (Player p : rankedList) {
            if ( ((RankedPlayer)p).rank == rank){
                return (RankedPlayer)p;
            }
        }
        System.out.println("Player not found.");
        return null;
    }
    
    
    /**
     * takes 2 players, the date, and the result of the match (for player 1) and
     * applies the Glicko algorithm to adjust their ratings and ratings deviation
     * @param p1
     * @param p2
     * @param date
     * @param p1Result 
     */
    public void match(RankedPlayer p1, RankedPlayer p2, String date, String p1Result){
        double rd1 = p1.ratingsDeviation;
        double rd2 = p2.ratingsDeviation;
        int r1 = p1.rating;
        int r2 = p1.rating;
        double result = -1;
        
        while (result == -1){
            switch (p1Result) {
                case "w":
                case "W":
                    result = 1;
                    p1.record = "W" + p1.record;
                    p2.record = "L" + p2.record;
                    break;
                case "l":
                case "L":
                    result = 0;                
                    p1.record = "L" + p1.record;
                    p2.record = "W" + p2.record;
                    break;
                case "d":
                case "D":
                    result = 0.5;
                    p1.record = "D" + p1.record;
                    p2.record = "D" + p2.record;
                    break;
                default:
                    System.out.println("Error, result must in the following format:"
                            + "\n Win: 'w' 'W'\t Loss: 'l' 'L'\t Draw: 'd' 'D'");
                    break;
            }
        }
        
        //Step 1 find the initial ratings deviation
        rd1 = Math.min(Math.sqrt(Math.pow(rd1, 2) + 34.6*monthsSince(p1.lastGame, date)), 350);
        rd2 = Math.min(Math.sqrt(Math.pow(rd2, 2) + 34.6*monthsSince(p2.lastGame, date)), 350);
        
        //Step 2 find attenuating factor 'f'
        double f1 = 1/(Math.sqrt(1+P*rd2));
        double f2 = 1/(Math.sqrt(1+P*rd1));
        
        //Step 3 find E
        double E1 = 1/(1+Math.pow(10, -(r1-r2) * (f1/400)));
        double E2 = 1/(1+Math.pow(10, -(r2-r1) * (f2/400)));
        
        //Step 4 K factor
        double K1 = Q*f1/(1/(Math.pow(rd1,2) + Math.pow(Q*f1, 2) * E1 * (1-E1)));
        double K2 = Q*f2/(1/(Math.pow(rd2,2) + Math.pow(Q*f2, 2) * E2 * (1-E2)));
        
        //Step 5 New rating
        p1.rating = (int) ( r1 + K1 * (result-E1) );
        p2.rating = (int) ( r2 + K2 * ( (1-result) -E2) );
        
        //Step 6 New ratings deviation
        p1.ratingsDeviation = (int) ( 1/(Math.sqrt((1/(Math.pow(rd1,2) + Math.pow(Q*f1, 2) * E1 * (1-E1))))) );
        p2.ratingsDeviation = (int) ( 1/(Math.sqrt((1/(Math.pow(rd2,2) + Math.pow(Q*f2, 2) * E2 * (1-E2))))) );
        
        p1.lastGame = date;
        p2.lastGame = date;
        
        bubbleRank();
        
    }
    
    
    /**
     * adds a match between two players, and adjusts record and date of last game
     * @param p1
     * @param p2
     * @param date
     * @param p1Result 
     */
    public void match(Player p1, Player p2, String date, String p1Result){
        int result = -1;
        
        while (result == -1){
            System.out.println("Enter the result for player 1");
            switch (p1Result) {
                case "w":
                case "W":
                    result = 1;
                    p1.record = "W" + p1.record;
                    p2.record = "L" + p2.record;
                    break;
                case "l":
                case "L":
                    result = 1;                
                    p1.record = "L" + p1.record;
                    p2.record = "W" + p2.record;
                    break;
                case "d":
                case "D":
                    result = 1;
                    p1.record = "D" + p1.record;
                    p2.record = "D" + p2.record;
                    break;
                default:
                    System.out.println("Error, result must in the following format:"
                            + "\n Win: 'w' 'W'\t Loss: 'l' 'L'\t Draw: 'd' 'D'");
                    break;
            }
        }
        
        p1.lastGame = date;
        p2.lastGame = date;
        
    }
            
            
    /**
     * deletes player p
     * @param p 
     */
    public void deletePlayer(Player p){
        playerList.remove(p);
        if (p instanceof RankedPlayer) {
            rankedList.remove(p);
            System.out.print("Ranked ");
        }
        System.out.println("Player removed");
    }
    
    
    /**
     * adds a new player to playerList
     * @param name
     */
    public void addPlayer(String name){
        Player p = new Player(name);
        playerList.add(p);
    }
    
    
    /**
     * adds a ranked player using the info from an existing unranked player
     * "raising" the player to become a ranked player
     * uses default rating 1000 and ratings deviation +/- 350
     * @param p
     */
    public void addRankedPlayer(Player p){
        RankedPlayer rP = new RankedPlayer(p.name, p.record, p.lastGame, 1000, 350);
        rankedList.add(rP);
        playerList.remove(p);
        playerList.add(rP);
        bubbleRank();
    }
    
    
    /**
     * adds a new Ranked Player to rankedList and playerList
     * @param name
     */
    public void addRankedPlayer(String name){
        RankedPlayer p = new RankedPlayer(name, "", "", 1000, 350);
        rankedList.add(p);
        playerList.add(p);
        bubbleRank();
    }
    
    
    /**
     * prints a list of ranked players in order of rank
     */
    public void printRankedList(){
        bubbleRank();
         for (Player p : rankedList){
             System.out.println((RankedPlayer) p );
         }
    }
    
    
    /**
     * prints a full list of all players from playerList
     */
    public void fullList(){
        for (Player p : playerList){
            if (p instanceof RankedPlayer){
                    System.out.println((RankedPlayer)p);
            } else{
                    System.out.println(p);
            }
        }
    }
    
    
    /**
     * prints a list of all players sorted by recent Wins/Draws/Losses
     */
    public void winDrawLossList(){
        
        for (Player p : playerList){
            if (!p.record.equals("")){
                if ((p.record).charAt(0) == 'W'){
                    if (p instanceof RankedPlayer){
                        System.out.println((RankedPlayer)p);
                    } else{
                        System.out.println(p);
                    }
                }
            }
        }
        for (Player p : playerList){
            if (!p.record.equals("")){
                if ((p.record).charAt(0) == 'D'){
                    if (p instanceof RankedPlayer){
                        System.out.println((RankedPlayer)p);
                    } else{
                        System.out.println(p);
                    }
                }
            }
        }
        
        for (Player p : playerList){
            if (!p.record.equals("")){
                if ((p.record).charAt(0) == 'L'){
                    if (p instanceof RankedPlayer){
                        System.out.println((RankedPlayer)p);
                    } else{
                        System.out.println(p);
                    }
                }
            }
        }
        
        for (Player p : playerList){
            if (p.record.equals("")){
                if (p instanceof RankedPlayer){
                    System.out.println((RankedPlayer)p);
                } else{
                    System.out.println(p);
                }
            }
        }
    }
    
    
    /**
     * prints a list sorted by recency of last game played
     */
    public void printRecentList(){
        bubbleRecent();
        for (Player p : playerList){
            if (p instanceof RankedPlayer){
                    System.out.println((RankedPlayer)p);
            } else{
                    System.out.println(p);
            }
        }
    }
    
    
}
