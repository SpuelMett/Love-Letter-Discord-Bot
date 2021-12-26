package CoreGame;

import Cards.ICard;
import IO.PrivateMessanger;
import net.dv8tion.jda.api.entities.User;

public class Player {


    private ICard mainCard;
    private ICard secondCard;
    private String name;
    private User user;

    public Player(String name, User user){
        this.name = name;
        this.user = user;
    }

    public void giveCard(ICard card){
        if(mainCard == null){
            mainCard = card;
        }
        else if(secondCard == null){
            secondCard = card;
        }
        //send privateMessageToShowCard
        PrivateMessanger privateMessanger = new PrivateMessanger();
        privateMessanger.sendCard(this, card);
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

    public String getName(){
        return name;
    }

    public User getUser(){
        return user;
    }

    /**
     * Returns the card the player wants to play
     * @param cardName
     * @return
     */
    public ICard getCardToPlay(String cardName){
        if(mainCard.getName().equals(cardName)){
            return mainCard;
        }
        if(secondCard.getName().equals(cardName)){
            return secondCard;
        }
        else return null;
    }
}
