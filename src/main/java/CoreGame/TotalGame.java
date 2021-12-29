package CoreGame;

import GameHandling.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.*;

public class TotalGame {

    private HashMap<User, Player> playerList;
    private HashMap<Player, Integer> scoreList;
    private static final int maxRounds = 4;
    private boolean isStarted;
    private boolean isRoundRunning;
    private Game game;
    private Player hostPlayer;
    private MessageChannel channel;



    public TotalGame(){
        playerList = new HashMap<>();
        scoreList = new HashMap<>();
        isStarted = false;
    }
    public MessageChannel getChannel() {
        return channel;
    }
    public void setChannel(MessageChannel channel){
        this.channel = channel;
    }

    public void startTotalGame(){
        isStarted = true;
    }

    /**
     * Starts a round of love-Letter.
     * @return
     */
    public String startGame(){
        if(!isStarted){
            Queue<Player> playerQueue = new LinkedList<>();
            for(Player player:playerList.values()){
                playerQueue.add(player);
            }
            game = new Game(playerQueue);
            isRoundRunning = true;
            isStarted = true;

            return game.firstTurn();
        }
        else return "The game is already running.";


    }

    public String addPlayer(User user, String name){
        if(!isStarted){
            Player player = new Player(name, user);
            playerList.put(user, player);
            scoreList.put(player, 0);
            return player.getName() + " is now participating.";
        }
        else return "The game is already running";
    }
    public void addHostPlayer(User user, String name){
        addPlayer(user, name);
        hostPlayer = playerList.get(user);
    }
    public String removePlayer(Player player){
        if(!isStarted) {
            playerList.remove(player.getUser());
            scoreList.remove(player);

            //check if player was the host
            if(hostPlayer.equals(player)){
                //needs to do something!!
                return "You were the host. Now we have a problem :(";
            }
            else return player.getName() + " is not participating anymore.";
        }
        else {
            return "The game is already running. You cant leave.";
        }
    }

    /**
     * Adds the score to a player.
     * @param player
     */
    public void addScore(Player player){
        int currentScore = scoreList.get(player);
        currentScore++;
        scoreList.replace(player, currentScore);
        if(currentScore >= maxRounds){
            //end the game
        }

        //Reset gamestarted
        reset();
    }

    private void reset(){
        isRoundRunning = false;
        isStarted = false;

        //reset all player
        for(Player player:playerList.values()){
            player.reset();
        }
    }

    public String getScoreDescription(){
        StringBuilder sb = new StringBuilder();
        sb.append("Scores: ").append("\n");
        Set<Player> playerSet = scoreList.keySet();
        for(Player player:playerSet){
            sb.append(player.getName()).append(": ").append(scoreList.get(player)).append("\n");
        }
        return sb.toString();
    }

    public Player getPlayer(User user) {
        return playerList.get(user);
    }

    /**
     * Run the card play and check if game is over
     * @param player
     * @param command
     * @return
     */
    public String playCard(Player player, Command command){
        if(isRoundRunning == false) return "The Round is not running! Type 'start' to start the game.";

        //run the play
        String result = game.playCard(player, command);

        //check if game is over
        if(game.isFinished()){
            result += "\n" + "The round is over. The winner is ";
            ArrayList<Player> winnerList = game.calcWinners();
            for(Player winner:winnerList){
                result += winner.getName() + "\n";
                addScore(winner);
            }
        }
        return result;

    }

    /**
     *
     * @param player
     * @param nr
     * @return
     */
    public void reactionResponse(Player player, int nr) {
        //only do something, when the round is running
        if(isRoundRunning) game.reactionResponse(player, nr, channel);
    }

}
