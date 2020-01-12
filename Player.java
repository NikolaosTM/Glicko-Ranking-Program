package topaloglou.mundy_niko_chess;

/**
 *
 * @author nikotopaloglou-mundy
 */
public class Player {
    String name;
    String record;
    String lastGame;

    public Player(String name, String record, String lastGame) {
        this.name = name;
        this.record = record;
        this.lastGame = lastGame;
    }
    
    public Player(String name) {
        this.name = name;
        this.record = "";
        this.lastGame = "";
    }

    @Override
    public String toString(){
        return "Player: " + name + "\n\tLast Game Played: " +
                lastGame + "\n\tRecord: " + record;
    }    
}
