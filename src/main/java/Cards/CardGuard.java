package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;

public class CardGuard implements ICard{

    private int value = 1;
    private String name = "Guard";
    private boolean isPlayOnPlayer = true;
    private boolean needsFourthWord = true;

    public CardGuard(){

    }

    /**
     * CoreGame.Player can guess a card
     */
    public String action(Player fromPlayer, Player onPlayer, Game game, Command command){
        //get
        ICard card = onPlayer.getCard();
        String cardName = command.getFourthWord().toLowerCase();

        //remove played card from fromPlayer
        fromPlayer.removeCard(this);

        if(card.getName().toLowerCase().equals(cardName)){
            //card matches

            //eliminate onPlayer
            game.eliminatePlayer(onPlayer);
            return fromPlayer.getName() + " guessed that " + onPlayer.getName() + " has a " + cardName + ". The guess was correct.";
        }
        else{
            //card does not match

            return fromPlayer.getName() + " guessed that " + onPlayer.getName() + " has a " + cardName + ". The guess was wrong.";
        }
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
