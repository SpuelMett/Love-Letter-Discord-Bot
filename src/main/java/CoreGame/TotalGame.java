package CoreGame;

import GameHandling.Command;
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

    public TotalGame(){
        playerList = new HashMap<>();
        scoreList = new HashMap<>();
        isStarted = false;
    }

    public void startTotalGame(){
        isStarted = true;
    }

    /**
     * Starts a round of love Lette.
     * @return
     */
    public String startGame(){
        Queue<Player> playerQueue = new LinkedList<>();
        for(Player player:playerList.values()){
            playerQueue.add(player);
        }
        game = new Game(playerQueue);
        isRoundRunning = true;
        isStarted = true;

        return game.firstTurn();
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
            playerList.remove(player);
            scoreList.remove(player);

            //check if player was the host
            if(hostPlayer.equals(player)){
                //needs to do something!!
                return "";
            }
            else return player.getName() + " is not participating anymore.";
        }
        else {
            return "The game is already running";
        }
    }

    public void addScore(Player player){
        int currentScore = scoreList.get(player);
        currentScore++;
        if(currentScore >= maxRounds){
            //end the game
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
    public String playCard(Player player, Command command){
        if(isRoundRunning == false) return "The Round is not running!";
        return game.playCard(player, command);
    }
}
