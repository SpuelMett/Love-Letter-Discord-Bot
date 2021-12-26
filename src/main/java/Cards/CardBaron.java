package Cards;

import CoreGame.Game;
import CoreGame.Player;

public class CardBaron implements ICard{

    private int value = 3;
    private String name = "Baron";
    private boolean isPlayOnPlayer = true;

    public CardBaron(){

    }

    /**
     * CoreGame.Player can compare a card
     */
    public String action(Player fromPlayer, Player onPlayer, Game game){
        return "";
    }

    public int getValue(){
        return value;
    }
    public String getName(){
        return name + ": " + value;
    }
    public boolean isPlayOnPlayer(){
        return isPlayOnPlayer;
    }
}
