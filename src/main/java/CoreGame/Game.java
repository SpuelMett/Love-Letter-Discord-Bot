package CoreGame;

import java.util.Queue;

public class Game {

    //currentPlayer is still in playerQueue
    private Player currentPlayer;
    private Queue<Player> playerQueue;


    public Game(){

    }

    public void eliminatePlayer(Player player){
        playerQueue.remove(player);
        //if(currentPlayer.equals(player)) currentPlayer = null;
        nextTurn();
        checkFinished();
    }

    public void nextTurn(){
        //remove currentPlayer from first position and queue it as last
        playerQueue.poll();
        playerQueue.add(currentPlayer);

        //select new currentPlayer
        currentPlayer = playerQueue.peek();
    }

    public boolean checkFinished(){
        if(playerQueue.size() <= 1) return true;

        return false;
    }

    public boolean isCurrentPlayer(Player player){
        return player.equals(currentPlayer);
    }
    public boolean isPlayer(Player player){
        return playerQueue.contains(player);
    }
}
