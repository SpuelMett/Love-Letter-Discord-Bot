package CoreGame;

import Cards.ICard;
import GameHandling.Command;
import GameHandling.Parser;
import IO.PrivateMessanger;
import IO.PublicMessanger;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.List;

public class PlayCard {

    private Game game;

    private Player fromPlayer;
    private ICard playingCard;
    private Player onPlayer;
    private String guess;

    private ICard card1;
    private ICard card2;

    private boolean isFinished;
    private List<Player> onPlayerList;

    public PlayCard(Player fromPlayer, Game game){
        this.fromPlayer = fromPlayer;
        this.game = game;
        isFinished = false;
    }

    public void addBothCards(){
        card1 = fromPlayer.getMainCard();
        card2 = fromPlayer.getSecondCard();
    }

    /**
     * Updates this object
     * @param cardNr
     * @return Returns the String that is shown in the private chat.
     */
    public void selectPlayingCard(int cardNr, MessageChannel channel){
        //Select the card
        if(cardNr == 1) playingCard = card1;
        else if(cardNr == 2) playingCard = card2;
        else return; // "This is not a playable card!";

        //checks if card needs a player to be played on
        if(!playingCard.isPlayOnPlayer()) isFinished = true;
        else askForOnPlayer(channel);
    }

    /**
     * Runs the card.
     * @return public Message String
     */
    public String action(){
        Command command;
        if(onPlayer == null){
            //simple card without onPlayer
            command = createCommand("play " + playingCard.getName().toLowerCase());
        }
        else if(guess == null){
            //card with onPlayer but without a guess
            command = createCommand("play " + playingCard.getName().toLowerCase() + " " +  onPlayer.getName().toLowerCase());
        }
        else{
            //complex card (guard) with a guess
            command = createCommand("play " + playingCard.getName().toLowerCase() + " " + onPlayer.getName().toLowerCase() + " " + guess);
        }
        String playResult = fromPlayer.getName() + " played a " + playingCard.getName() + "." + "\n";
        playResult += playingCard.action(fromPlayer, onPlayer, game, command);
        return playResult;
    }

    public void selectAny(int nr, MessageChannel channel){
        if(playingCard == null) selectPlayingCard(nr, channel);
        else if(onPlayer == null) selectOnPlayer(nr, channel);
        else if(guess == null) selectGuess(nr, channel);
    }

    /**
     * Returns string that is shown in the public Chat
     * @param nr
     * @param channel
     * @return
     */
    public void selectOnPlayer(int nr, MessageChannel channel){
        if(nr < 1 || nr > onPlayerList.size()) {
            //wrong number
            PublicMessanger publicMessanger = new PublicMessanger();
            publicMessanger.sendPublicMessage(channel, "This is not a valid player to play on.");
        }
        else{
            //get onPlayer
            onPlayer = onPlayerList.get(nr - 1);

            //check if card needs a fourth word
            if(!playingCard.isNeedsFourthWord()){
                //
                isFinished = true;
            }
            else{
                askForFourthWord(channel);
            }
        }
    }

    public void selectGuess(int nr, MessageChannel channel){
        if(nr < 1 || nr > 7){
            //wrong number
            PublicMessanger publicMessanger = new PublicMessanger();
            publicMessanger.sendPublicMessage(channel, "This is not a valid card to guess");
        }
        else{
            guess = switch (nr) {
                case 1 -> "priest";
                case 2 -> "baron";
                case 3 -> "handmaid";
                case 4 -> "prince";
                case 5 -> "king";
                case 6 -> "countess";
                case 7 -> "princess";
                default -> "";
            };
            isFinished = true;
        }
    }

    private Command createCommand(String input){
        Parser parser = new Parser();
        Command command = parser.createCommand(input);
        return command;
    }

    /**
     * Asks the player in the public channel to select a player.
     * @param channel
     */
    public void askForOnPlayer(MessageChannel channel){
        String result = "You are playing the " + playingCard.getName() + "." + "\n";
        result += "Please select a player: " + "\n";

        //get all playable player
        onPlayerList =  game.getPlayerList();

        //Iterate over Playerlist: add player or delete protected player
        int i = 0;
        while (i < onPlayerList.size()){
            Player player = onPlayerList.get(i);
            if(player.isProtected()) onPlayerList.remove(i);
            else{
                i++;
                result += i + ": " + player.getName() + "\n";
            }
        }

        //make a new message
        PrivateMessanger pm = new PrivateMessanger();
        Message message = pm.sendPrivateMessage(fromPlayer.getUser(), result);
        pm.addReaction(message, i);
    }

    public void askForFourthWord(MessageChannel channel){
        String result = fromPlayer.getName() + " plays the " + playingCard.getName() + " on " + onPlayer.getName() + ".";
        result += "\n" + "What card do you want to guess?" + "\n";

        result += "1: Priest (2)" + "\n";
        result += "2: Baron (3)" + "\n";
        result += "3: Handmaid (4)" + "\n";
        result += "4: Prince (5)" + "\n";
        result += "5: King (6)" + "\n";
        result += "6: Countess (7)" + "\n";
        result += "7: Princess (8)" + "\n";

        PrivateMessanger pm = new PrivateMessanger();
        Message message = pm.sendPrivateMessage(fromPlayer.getUser(), result);
        pm.addReaction(message, 7);
    }

    public boolean isFinished(){
        return isFinished;
    }
}
