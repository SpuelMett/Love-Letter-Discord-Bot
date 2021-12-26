package CoreGame;

import Cards.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Stapel {

    Stack<ICard> cardStack;

    public Stapel(){
        cardStack = new Stack<>();

        //ArrayList<ICard> cardArrayList = new ArrayList<>();
        ICard[] cards = new ICard[9];

        cards[0] = new CardGuard();
        cards[1] = new CardGuard();
        cards[2] = new CardGuard();
        cards[3] = new CardGuard();
        cards[4] = new CardGuard();

        cards[5] = new CardPriest();
        cards[6] = new CardPriest();

        cards[7] = new CardBaron();
        cards[8] = new CardBaron();

        createStack(mix(cards));
    }

    private void createStack(ICard[] cards){
        for(ICard card:cards){
            cardStack.add(card);
        }
    }

    private ICard[] mix(ICard[] cards){
        // Creating a object for Random class
        Random r = new Random();

        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = cards.length -1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = r.nextInt(i+1);

            // Swap arr[i] with the element at random index
            ICard temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
        return cards;
    }

    public ICard drawCard(){
        return cardStack.pop();
    }
}
