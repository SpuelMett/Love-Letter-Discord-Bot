package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;

public class CardPrince implements ICard{

    private int value = 5;
    private String name = "Prince";
    private boolean isPlayOnPlayer = true;
    private boolean needsFourthWord = false;

    public CardPrince(){

    }

    public String action(Player fromPlayer, Player onPlayer, Game game, Command command){
        //remove played card from fromPlayer
        fromPlayer.removeCard(this);

        //remove card from onPlayer
        String result = onPlayer.dropCard();

        //give onPlayer a new Card
        game.giveCard(onPlayer);

        return result;

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
