# Glicko-Ranking-Program
Tracks a group of casual and ranked chess players, that can be loaded/saved to/from a .txt file
Outputs all data for a single player, or a list of players sorted by Rank/Record/Most Recent Game
Ranked chess players are ranked using the Glicko ranking system.
Can input a ranked or a casual match between two players, such that both players are ranked players in a ranked match
 - for a ranked match each players rating is updated accordingly, based off the glicko system
 - for all matches the players record and most recent game played are updated

Player.java creates a "Player" class including 3 strings
 - The players Name (ex. Joe)
 - The players record (ex: WDLWDL) (W means a win, D means a draw, L means a loss)
 - The date of the most recent game played with the following format (mm/yyyy)
 
 RankedPlayer.java extends the "Player" class and adds 3 ints
  - Glicko rating (ex: 1000)
  - Ratings deviation (ex: Rating +/- 100)
  - Rank, (compared with other ranked players)
  
  Chess.java contains all necessary methods to
   - load/save data to/from a txt file
   - add/remove/modify a player
   - find and print a player by name/rank
   - print a list of players sorted by rank/record/last game
   - to input a new match
   
   Topaloglou-Mundy_Niko_Chess.java contains the main method, puts methods from Chess.java in a menu format
