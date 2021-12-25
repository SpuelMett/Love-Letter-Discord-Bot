package CoreGame;

import Cards.ICard;

public class Player {


    private ICard mainCard;
    private ICard secondCard;

    public Player(){

    }

    public void drawCard(Stapel stapel){
        if(mainCard == null){
            mainCard = stapel.drawCard();
        }
        else if(secondCard == null){
            ICard newCard = stapel.drawCard();
            secondCard = newCard;
        }
    }

    /**
     * Ask the player which card to play
     */
    public void askToPlay(){
        //private Message with options
    }

    public void playCard(boolean main){
        if(main) mainCard.action();
        else secondCard.action();
    }

    public ICard getCard(){
        return mainCard;
    }
}
