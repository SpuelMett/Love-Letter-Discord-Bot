package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;

public class CardKing implements ICard{

    private int value = 6;
    private String name = "King";
    private boolean isPlayOnPlayer = true;
    private boolean needsFourthWord = false;

    public CardKing(){

    }

    public String action(Player fromPlayer, Player onPlayer, Game game, Command command){
        //remove played card from fromPlayer
        fromPlayer.removeCard(this);

        ICard temp = fromPlayer.getCard();
        fromPlayer.dropCard();
        fromPlayer.giveCard(onPlayer.getCard());
        onPlayer.dropCard();
        onPlayer.giveCard(temp);

        return onPlayer.getName() + " and " + fromPlayer.getName() + " switched cards. You received you new cards.";
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
