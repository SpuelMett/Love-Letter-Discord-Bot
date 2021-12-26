package Cards;

import CoreGame.Game;
import CoreGame.Player;

public class CardPrincess {

    private int value = 8;
    private String name = "Princess";
    private boolean isPlayOnPlayer = false;

    public CardPrincess(){

    }

    /**
     * CoreGame.Player can compare a card
     */
    public String action(Game game, Player player){
        game.eliminatePlayer(player);
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
