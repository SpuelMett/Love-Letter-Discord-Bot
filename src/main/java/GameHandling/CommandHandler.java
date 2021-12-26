package GameHandling;

import CoreGame.Player;
import CoreGame.TotalGame;

public class CommandHandler {

    private final TotalGame totalGame;
    private final Player currentPlayer;
    private final Command command;

    public CommandHandler(Player player, Command command, TotalGame game){
        this.command = command;
        this.currentPlayer = player;
        this.totalGame = game;
    }

    /**
     * redirects the process to the right method.
     * @return
     */
    public String processCommand(){
        if(command == null){
            return "I don't know what you want.";
        }
        String commandString = command.getCommand();
        if(commandString.equals("score")) return score();
        if(commandString.equals("leave")) return leave();
        if(commandString.equals("start")) return start();
        if(commandString.equals("play")) return play();

        return "I don't know what you want.";
    }

    public String score(){
        return totalGame.getScoreDescription();
    }
    public String leave(){
        return totalGame.removePlayer(currentPlayer);
    }
    public String start(){
        return totalGame.startGame();
    }
    public String play(){
        return totalGame.playCard(currentPlayer, command);
    }

}
