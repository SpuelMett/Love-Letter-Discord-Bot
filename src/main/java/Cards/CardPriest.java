package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;
import IO.PrivateMessenger;

public class CardPriest implements ICard{

    private int value = 2;
    private String name = "Priest";
    private boolean isPlayOnPlayer = true;
    private boolean needsFourthWord = false;

    public CardPriest(){

    }

    /**
     * CoreGame.Player can look up a Card
     */
    public String action(Player fromPlayer, Player onPlayer, Game game, Command command){
        //get
        ICard card = onPlayer.getCard();

        //make content
        String content = onPlayer.getName() + " has a " + card.getName();

        //private Message to from Player
        PrivateMessenger pm = new PrivateMessenger();
        pm.sendPrivateMessage(fromPlayer.getUser(), content);

        //remove played card from fromPlayer
        fromPlayer.removeCard(this);

        return fromPlayer.getName() + " looked at " + onPlayer.getName() + " card.";
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
