package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;

public class CardHandmaid implements ICard{
    private int value = 4;
    private String name = "Handmaid";
    private boolean isPlayOnPlayer = false;
    private boolean needsFourthWord = false;

    public CardHandmaid(){

    }

    public String action(Player fromPlayer, Player onPlayer, Game game, Command command){
        fromPlayer.setProtected();

        //remove played card from fromPlayer
        fromPlayer.removeCard(this);

        return fromPlayer.getName() + " is protected for one round.";
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
