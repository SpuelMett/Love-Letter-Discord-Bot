package Cards;

import Cards.ICard;
import CoreGame.Game;
import CoreGame.Player;

public class Card implements ICard {

    private int value;
    private String name;
    private boolean isPlayOnPlayer = false;

    public Card(){

    }

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
