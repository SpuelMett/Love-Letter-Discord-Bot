package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;

public class CardBaroness implements ICard{

    private int value = 3;
    private String name = "Baroness";
    private boolean isPlayOnPlayer = true;
    private boolean needsFourthWord = false;

    public CardBaroness(){

    }

    /**
     * CoreGame.Player can compare a card
     */
    public String action(Player fromPlayer, Player onPlayer, Game game, Command command){
        //remove played card from fromPlayer
        fromPlayer.removeCard(this);

        ICard fromPlayerCard = fromPlayer.getCard();
        ICard onPlayerCard = onPlayer.getCard();

        int fromPlayerCardValue = fromPlayerCard.getValue();
        int onPlayerCardValue = onPlayerCard.getValue();

        //same
        if(fromPlayerCardValue == onPlayerCardValue){
            return "Both cards have the same value.";
        }
        //from Player won
        else if(fromPlayerCardValue > onPlayerCardValue){
            game.eliminatePlayer(fromPlayer);
            return fromPlayer.getName() + " lost. " + fromPlayer.getName() + " had a " + fromPlayerCard.getName();
        }
        else {
            game.eliminatePlayer(onPlayer);
            return onPlayer.getName() + " lost. " + onPlayer.getName() + " had a " + onPlayerCard.getName();
        }
    }

    public int getValue(){
        return value;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return name + " (" + value + ")";
    }
    public boolean isPlayOnPlayer(){
        return isPlayOnPlayer;
    }
    public boolean isNeedsFourthWord(){
        return needsFourthWord;
    }
}
