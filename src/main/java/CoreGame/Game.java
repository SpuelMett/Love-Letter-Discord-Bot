package CoreGame;

import Cards.ICard;
import GameHandling.Command;
import IO.PublicMessenger;
import net.dv8tion.jda.api.entities.MessageChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * if the game is finished after the round the names are printed out
 */
public class Game {

    //currentPlayer is still in playerQueue
    private Player currentPlayer;
    private PlayCard playCard;
    private final Queue<Player> playerQueue;
    private final Stapel stapel;

    /**
     *
     * @param playerQueue list of player that take part
     */
    public Game(Queue<Player> playerQueue){
        this.playerQueue = playerQueue;
        stapel = new Stapel();

        //give one card to all player
        giveAllCards();
    }

    /**
     * Gives the player a new card from the card stack
     * @param player Player who gets the card
     */
    public void giveCard(Player player){
        ICard card = stapel.drawCard();
        player.giveCard(card);
    }

    /**
     * Give all player a new card
     */
    public void giveAllCards(){
        for(Player player:playerQueue){
            giveCard(player);
        }
    }

    /**
     * Removes the player from the active player list if he lost.
     * @param player player that lost
     */
    public void eliminatePlayer(Player player){
        playerQueue.remove(player);
        if(currentPlayer.equals(player)) currentPlayer = null;
    }

    public List<Player> getPlayerList(){
        return playerQueue.stream().toList();
    }

    /**
     * Prepares the first round of the game
     * @return public message
     */
    public String firstTurn(){
        currentPlayer = playerQueue.peek();
        playCard = new PlayCard(currentPlayer, this);
        String output = "Everyone got their cards!" + "\n" +  currentPlayer.getName() +  " is on turn.";
        askForAction();
        return output;
    }

    /**
     * Prepares the next Turn.
     * Select the new current Player and ask him to do his move
     * @return text for the public channel
     */
    public String nextTurn(){
        if(currentPlayer != null){
            //remove currentPlayer from first position and queue it as last
            playerQueue.poll();
            playerQueue.add(currentPlayer);
        }
        //select new currentPlayer
        currentPlayer = playerQueue.peek();
        playCard = new PlayCard(currentPlayer, this);

        return askForAction();
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

    /**
     * Player wants to play a card.
     * Gets the command from a player and tries to make his play
     * It must be checked if the play is valid.
     * @param player player playing the card
     * @param command
     * @return
     */
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
                            if(command.getFourthWord().equalsIgnoreCase("guard")) return "You cant guess the guard!";
                        }

                        //play card
                        result += makePlayCard(card, currentPlayer, onPlayer, command);
                    }
                }
                //Play card
                else{
                    result += makePlayCard(card, currentPlayer, null, command);
                }
            }
        }
        else {
            return  "Its not your turn.";
        }

        //check if the game is finished after the play of the player
        if(isFinished()){
            //print who won and set t
            result += "\n" + "The round is over. The winner is: ";
            for(Player wonPlayer : calcWinners()){
                result += wonPlayer.getName() + " ";
            }
        }
        return result;
    }

    /**
     *
     * @param card card to play
     * @param currentPlayer player that plays the card
     * @param onPlayer player that is being targeted
     * @param command command object
     * @return
     */
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

    /**
     * Check if the game is finished.
     * The game is finished if only one player is left or the card stack is empty.
     * @return
     */
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

    /**
     * Calculates who won the game. Returns a list of the winners
     * @return
     */
    public ArrayList<Player> calcWinners(){
        ArrayList<Player> maxPlayerList = new ArrayList<>();
        if(playerQueue.size() == 1){
            maxPlayerList.add(playerQueue.poll());
        }
        else{
            //check who has the highest card
            int maxValue = 0;

            //iterate over every player left
            for(Player player:playerQueue){
                //get card value of the player
                int currentValue = player.getCard().getValue();

                //ad player to the list if it has the same value as the previous highest value
                if(currentValue == maxValue){
                    maxPlayerList.add(player);
                }
                //if the player has a higher card, clear the list, add the player and set the new max value
                if(currentValue >= maxValue){
                    maxValue = currentValue;
                    maxPlayerList.clear();
                    maxPlayerList.add(player);
                }
            }
        }
        return maxPlayerList;
    }

    /**
     * Checks if a string refers to a card of the game
     * @param name of the card
     * @return
     */
    private boolean isValidCardName(String name){
        return switch (name) {
            case "guard", "priest", "baron", "handmaid", "prince", "king", "countess", "princess" -> true;
            default -> false;
        };
    }

    /**
     * Triggered when a player of the current game adds a reaction
     * Return the private Message.
     * @param player
     * @param cardNr
     * @param channel public messageChannel
     */
    public void reactionResponse(Player player, int cardNr, MessageChannel channel){
        //check if player is the current player
        if(!player.equals(currentPlayer)) return; // "Its not your turn.";

        //update playCard
        playCard.selectAny(cardNr, channel);

        //check if playCard is finished. If yes ...
        if(playCard.isFinished()){
            //Run the card play
            String result = playCard.action() + "\n";

            //if the game is not finished after that play start the next turn
            if(!isFinished()) result += nextTurn();

            //send result to public channel
            new PublicMessenger().sendPublicMessage(channel, result);
        }
    }
}
