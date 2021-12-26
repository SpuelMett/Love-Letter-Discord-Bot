package Cards;

import CoreGame.Game;
import CoreGame.Player;
import IO.PrivateMessanger;

public class CardPriest implements ICard{

    private int value = 2;
    private String name = "Priest";
    private boolean isPlayOnPlayer = true;

    public CardPriest(){

    }

    /**
     * CoreGame.Player can look up a Card
     */
    public String action(Player fromPlayer, Player onPlayer, Game game){
        //get
        ICard card = onPlayer.getCard();

        //make content
        String content = onPlayer.getName() + " has a " + card.getName();

        //private Message to from Player
        PrivateMessanger pm = new PrivateMessanger();
        pm.sendPrivateMessage(fromPlayer.getUser(), content);

        return fromPlayer.getName() + " looked at " + onPlayer.getName() + " card.";
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
