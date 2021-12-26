package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;

public class CardPrincess implements ICard{

    private int value = 8;
    private String name = "Princess";
    private boolean isPlayOnPlayer = false;
    private boolean needsFourthWord = false;

    public CardPrincess(){

    }

    /**
     * CoreGame.Player can compare a card
     */
    public String action(Player fromPlayer, Player onPlayer, Game game, Command command){
        game.eliminatePlayer(fromPlayer);
        return fromPlayer.getName() + " played the Princess and lost.";
    }

    public int getValue(){
        return value;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return name + ": " + value;
    }
    public boolean isPlayOnPlayer(){
        return isPlayOnPlayer;
    }
    public boolean isNeedsFourthWord(){
        return needsFourthWord;
    }
}
