package CoreGame;

import Cards.ICard;
import IO.PrivateMessenger;
import net.dv8tion.jda.api.entities.User;

public class Player {


    private ICard mainCard;
    private ICard secondCard;
    private final String name;
    private final User user;
    private boolean isProtected;

    public Player(String name, User user){
        this.name = name;
        this.user = user;
        isProtected = false;
    }

    public void giveCard(ICard card){
        PrivateMessenger privateMessenger = new PrivateMessenger();
        if(mainCard == null){
            mainCard = card;
            privateMessenger.sendCard(this, card);
        }
        else if(secondCard == null){
            secondCard = card;
            privateMessenger.sendCardSelection(this, mainCard, secondCard);
        }
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

    public ICard getMainCard(){
        return mainCard;
    }
    public ICard getSecondCard(){
        return secondCard;
    }

    /**
     * Returns the card the player wants to play
     * @param cardName
     * @return
     */
    public ICard getCardToPlay(String cardName){
        removeProtected();
        if(mainCard.getName().toLowerCase().equals(cardName)){
            return mainCard;
        }
        else if(secondCard.getName().toLowerCase().equals(cardName)){
            return secondCard;
        }
        else return null;
    }

    public void removeCard(ICard card){
        if(mainCard.equals(card)){
            mainCard = secondCard;
            secondCard = null;
        }
        else if(secondCard.equals(card)){
            secondCard = null;
        }
    }

    /**
     * Player drops its card.
     * Used when attacked by Princess.
     * @return
     */
    public String dropCard(){
        String result;
        if(mainCard.getName().equals("Princess")){
            result = name + " dropped the princess and was eliminated.";
        }
        else{
            result = name + " dropped a " + mainCard.getName() + ".";
        }
        mainCard = null;
        return result;
    }

    public void setProtected(){
        isProtected = true;
    }
    public void removeProtected(){
        isProtected = false;
    }
    public boolean isProtected(){
        return isProtected;
    }

    public void reset(){
        mainCard = null;
        secondCard = null;
        isProtected = false;
    }
}
