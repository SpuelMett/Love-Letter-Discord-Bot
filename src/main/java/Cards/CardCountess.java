package Cards;

import CoreGame.Game;
import CoreGame.Player;

public class CardCountess implements ICard {

    private int value = 7;
    private String name = "Countess";
    private boolean isPlayOnPlayer = false;

    public CardCountess(){

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
