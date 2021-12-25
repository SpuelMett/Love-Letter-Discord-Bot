package CoreGame;

import Cards.Card;
import Cards.ICard;

import java.util.Stack;

public class Stapel {

    Stack<ICard> cardStack;

    public Stapel(){
        cardStack = new Stack<>();
    }

    private void mix(){

    }

    public ICard drawCard(){
        return cardStack.pop();
    }
}
