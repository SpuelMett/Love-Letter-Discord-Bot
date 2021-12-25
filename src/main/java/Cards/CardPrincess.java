package Cards;

import CoreGame.Game;

public class CardPrincess {

    private int value = 8;

    public CardPrincess(){

    }

    /**
     * CoreGame.Player can compare a card
     */
    public void action(Game game){
        game.eliminatePlayer();
    }

    public int getValue(){
        return value;
    }
}
