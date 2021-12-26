package Cards;

import CoreGame.Game;
import CoreGame.Player;

public class CardGuard implements ICard{

    private int value = 1;
    private String name = "Guard";
    private boolean isPlayOnPlayer = true;

    public CardGuard(){

    }

    /**
     * CoreGame.Player can guess a card
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
