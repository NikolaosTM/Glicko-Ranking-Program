/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topaloglou.mundy_niko_chess;

/**
 *
 * @author nikotopaloglou-mundy
 */
public class RankedPlayer extends Player{
    int rating;
    int ratingsDeviation;
    int rank;

    public RankedPlayer( String name, String record, String lastGame, int rating, int ratingsDeviation) {
        super(name, record, lastGame);
        this.rating = rating;
        this.ratingsDeviation = ratingsDeviation;
    }

    @Override
    public String toString(){
        return "Player: " + name + ", Rank: "+ rank + 
                "\n\tRating: " + rating + " +/- " + ratingsDeviation +
                "\n\tLast Game Played: " +
                lastGame + "\n\tRecord: " + record;
    }
    
    
    
}
