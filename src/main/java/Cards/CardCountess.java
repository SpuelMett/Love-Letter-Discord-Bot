package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;

public class CardCountess implements ICard {

    private int value = 7;
    private String name = "Countess";
    private boolean isPlayOnPlayer = false;
    private boolean needsFourthWord = false;

    public CardCountess(){

    }

    /**
     * CoreGame.Player can compare a card
     */
    public String action(Player fromPlayer, Player onPlayer, Game game, Command command){
        //remove played card from fromPlayer
        fromPlayer.removeCard(this);

        return fromPlayer.getName() + " played the Countess.";
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
