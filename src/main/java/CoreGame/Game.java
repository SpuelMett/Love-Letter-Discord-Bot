package CoreGame;

import Cards.ICard;
import GameHandling.Command;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Queue;

public class Game {

    //currentPlayer is still in playerQueue
    private Player currentPlayer;
    private Queue<Player> playerQueue;
    private Stapel stapel;


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

    public String firstTurn(){
        currentPlayer = playerQueue.peek();
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

        return askForAction();
        //return currentPlayer.getName() +  " is on turn.";
    }

    /**
     * Gives the player a new Card and ask for action
     * @return
     */
    public String askForAction(){
        giveCard(currentPlayer);
        return currentPlayer.getName() + " its your turn.";
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
            if(command.hasSecondWord() == false){
                return "Please specify which card you want to play";
            }
            else{
                String cardName = command.getSecondWord();
                ICard card = player.getCardToPlay(cardName);
                if(card == null) return "You don't have this card!.";

                //check if card needs to be played on a player
                if(card.isPlayOnPlayer() == true){
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

                        //play
                        result += card.action(currentPlayer, onPlayer, this, command);
                        result += "\n";
                        result += nextTurn();
                        return result;
                    }
                }
                //Play card
                else{
                    result += card.action(currentPlayer, null, this, command);
                    result += "\n";
                    result += nextTurn();
                    return result;
                }
            }
        }
        else return "Its not your turn.";
    }

    private Player getPlayerByName(String name){
        for(Player player:playerQueue){
            if(player.getName().toLowerCase().equals(name)) return player;
        }
        return null;
    }

    public boolean isFinished(){
        //ceck if only one player  is left
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
        boolean valid = false;
        switch (name){
            case "guard":
            case "priest":
            case "baron":
            case "handmaid":
            case "prince":
            case "countess":
            case "princess":
                valid = true;
                break;
            default: valid =  false;
        }
        return valid;
    }

}
