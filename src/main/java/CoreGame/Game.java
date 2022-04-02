package CoreGame;

import Cards.ICard;
import GameHandling.Command;
import IO.PublicMessenger;
import net.dv8tion.jda.api.entities.MessageChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Game {

    //currentPlayer is still in playerQueue
    private Player currentPlayer;
    private PlayCard playCard;
    private Queue<Player> playerQueue;
    private final Stapel stapel;


    public Game(Queue<Player> playerQueue){
        this.playerQueue = playerQueue;
        stapel = new Stapel();

        //give one card to all player
        giveAllCards();
    }

    public void giveCard(Player player){
        ICard card = stapel.drawCard();
        player.giveCard(card);
    }
    public void giveAllCards(){
        for(Player player:playerQueue){
            giveCard(player);
        }
    }

    public void eliminatePlayer(Player player){
        playerQueue.remove(player);
        if(currentPlayer.equals(player)) currentPlayer = null;
        //nextTurn();
    }

    public List<Player> getPlayerList(){
        return playerQueue.stream().toList();
    }

    public String firstTurn(){
        currentPlayer = playerQueue.peek();
        playCard = new PlayCard(currentPlayer, this);
        String output = "Everyone got their cards!" + "\n" +  currentPlayer.getName() +  " is on turn.";
        askForAction();
        return output;
    }

    public String nextTurn(){
        if(currentPlayer != null){
            //remove currentPlayer from first position and queue it as last
            playerQueue.poll();
            playerQueue.add(currentPlayer);

            //select new currentPlayer
        }
        //select new currentPlayer
        currentPlayer = playerQueue.peek();
        playCard = new PlayCard(currentPlayer, this);

        return askForAction();
        //return currentPlayer.getName() +  " is on turn.";
    }

    /**
     * Gives the player a new Card and ask for action
     * @return
     */
    public String askForAction(){
        //give a second card to the player
        giveCard(currentPlayer);
        //update playCard object
        playCard.addBothCards();
        return currentPlayer.getName() + " it is your turn.";
    }

    public boolean isCurrentPlayer(Player player){
        return player.equals(currentPlayer);
    }
    public boolean isPlayer(Player player){
        return playerQueue.contains(player);
    }

    public String playCard(Player player, Command command){
        String result = "";
        if(currentPlayer.equals(player)){
            if(!command.hasSecondWord()){
                return "Please specify which card you want to play.";
            }
            else{
                String cardName = command.getSecondWord();
                ICard card = player.getCardToPlay(cardName);
                if(card == null) return "You don't have this card!";

                //check if card needs to be played on a player
                if(card.isPlayOnPlayer()){
                    if(command.getThirdWord() == null){
                        return "Please specify on which player you want to play.";
                    }
                    //play a card on a player
                    else{
                        //get Player from third word
                        Player onPlayer = getPlayerByName(command.getThirdWord());

                        if(onPlayer == null) return "This player is not in the game!";
                        if(onPlayer.isProtected()) return "This player is protected.";

                        //check if card needs a fourth word
                        if(card.isNeedsFourthWord()){
                            if(!command.hasFourthWord()) return "Please say also what you want to guess.";
                            if(!isValidCardName(command.getFourthWord().toLowerCase())) return command.getFourthWord() + " is not a valid card name.";
                            if(command.getFourthWord().toLowerCase().equals("guard")) return "You cant guess the guard!";
                        }

                        //play card
                        result += makePlayCard(card, currentPlayer, onPlayer, command);
                        return result;
                    }
                }
                //Play card
                else{
                    result += makePlayCard(card, currentPlayer, null, command);
                    return result;
                }
            }
        }
        else return "Its not your turn.";
    }

    private String makePlayCard(ICard card,Player currentPlayer, Player onPlayer, Command command){
        //Play the card
        String result = card.action(currentPlayer, onPlayer, this, command);
        //if not finished start the next turn
        if(!isFinished()){
            result += "\n";
            result += nextTurn();
        }
        return result;
    }

    private Player getPlayerByName(String name){
        for(Player player:playerQueue){
            if(player.getName().toLowerCase().equals(name)) return player;
        }
        return null;
    }

    public boolean isFinished(){
        //check if only one player  is left
        if(playerQueue.size() < 2){
            return true;
        }

        //check if Stapel is empty
        if(stapel.isEmpty()){
            return true;
        }
        return false;
    }

    public ArrayList<Player> calcWinners(){
        ArrayList<Player> maxPlayerList = new ArrayList<>();
        if(playerQueue.size() == 1){
            maxPlayerList.add(playerQueue.poll());
            return maxPlayerList;
        }
        else{
            //who has the highest card
            int maxValue = 0;

            for(Player player:playerQueue){
                int currentValue = player.getCard().getValue();
                if(currentValue == maxValue){
                    maxPlayerList.add(player);
                }
                if(currentValue >= maxValue){
                    maxValue = currentValue;
                    maxPlayerList.clear();
                    maxPlayerList.add(player);
                }
            }
        }
        return maxPlayerList;
    }

    private boolean isValidCardName(String name){
        return switch (name) {
            case "guard", "priest", "baron", "handmaid", "prince", "king", "countess", "princess" -> true;
            default -> false;
        };
    }

    /**
     * Return the private Message.
     * @param player
     * @param cardNr
     * @param channel is the public messageChannel
     * @return private Message
     */
    public void reactionResponse(Player player, int cardNr, MessageChannel channel){
        //check if player is the current player
        if(!player.equals(currentPlayer)) return; // "Its not your turn.";

        //update playCard
        playCard.selectAny(cardNr, channel);

        //check if playCard is finished. If yes ...
        if(playCard.isFinished()){
            //Next turn
            String result = playCard.action() + "\n";
            result += nextTurn();

            //send result to public channel
            PublicMessenger publicMessenger = new PublicMessenger();
            publicMessenger.sendPublicMessage(channel, result);
        }
    }
}
