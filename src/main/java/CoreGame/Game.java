package CoreGame;

import Cards.ICard;
import GameHandling.Command;

import java.util.Queue;

public class Game {

    //currentPlayer is still in playerQueue
    private Player currentPlayer;
    private Queue<Player> playerQueue;
    private Stapel stapel;


    public Game(Queue<Player> playerQueue){
        this.playerQueue = playerQueue;
        stapel = new Stapel();
        giveAllCards();
    }

    public void giveCard(Player player){
        ICard card = stapel.drawCard();
        player.giveCard(card);
    }
    public void giveAllCards(){
        for(Player player:playerQueue){
            giveCard(player);
        }
    }

    public void eliminatePlayer(Player player){
        playerQueue.remove(player);
        //if(currentPlayer.equals(player)) currentPlayer = null;
        nextTurn();
        checkFinished();
    }

    public String firstTurn(){
        currentPlayer = playerQueue.peek();
        String output = "Everyone got it cards!" + "\n" +  currentPlayer.getName() +  " is on turn.";
        askForAction();
        return output;
    }

    public String nextTurn(){
        //remove currentPlayer from first position and queue it as last
        playerQueue.poll();
        playerQueue.add(currentPlayer);

        //select new currentPlayer
        currentPlayer = playerQueue.peek();

        return currentPlayer.getName() +  " is on turn.";
    }

    /**
     * Gives the player a new Card and ask for action
     * @return
     */
    public String askForAction(){
        giveCard(currentPlayer);
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

    public String playCard(Player player, Command command){
        if(currentPlayer.equals(player)){
            if(command.hasSecondWord() == false){
                return "Please specify which card you want to play";
            }
            else{
                String cardName = command.getSecondWord();
                ICard card = player.getCardToPlay(cardName);
                if(card == null) return "You don't have this card!.";

                //check if card needs to be played on a player
                if(card.isPlayOnPlayer() == true){
                    if(command.getThirdWord() == null){
                        return "Please specify on which player you want to play.";
                    }
                    //play a card on a player
                    else{
                        //get Player from third word
                        Player onPlayer = getPlayerByName(command.getThirdWord());

                        if(onPlayer == null) return "This player is not in the game!";

                        //play
                        return card.action(currentPlayer, onPlayer, this);

                    }
                }
                //Play card
                else{
                    return card.action(currentPlayer, null, this);
                }
            }
        }
        else return "Its not your turn.";
    }

    private Player getPlayerByName(String name){
        for(Player player:playerQueue){
            if(player.getName().equals(name)) return player;
        }
        return null;
    }

}
