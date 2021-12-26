package IO;

import CoreGame.Game;
import CoreGame.Player;
import CoreGame.TotalGame;
import GameHandling.Command;
import GameHandling.CommandHandler;
import GameHandling.GameHandler;
import GameHandling.Parser;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.Locale;


public class BasicInputHandling {

    private GameHandler gameHandler;

    public BasicInputHandling(){
        gameHandler = new GameHandler();
    }

    public String handlePrivateInput(Message msg){
        //check if any Game exists with this server
        User user = msg.getAuthor();
        TotalGame totalGame = gameHandler.findPlayer(user);
        if(totalGame == null){
            return "You don't play this game";
        }
        else{
            String message = msg.getContentRaw().toLowerCase();
            message = message.substring(2);
            Parser parser = new Parser();
            Command command = parser.createCommand(message);
            Player player = getPlayer(msg, totalGame);

            //run command
            CommandHandler commandHandler = new CommandHandler(player, command, totalGame);
            String result = commandHandler.processCommand();
            return result;
        }
    }


    /**
     * Handles the input Message. Checks server and Player and redirects command to the command handler. Returns the answer.
     * @param msg
     * @return
     */
    public String handleInput(Message msg){

        //get Server
        Guild guild = msg.getGuild();

        //get the CoreGame.Game Object of the Server
        TotalGame currentGame = gameHandler.getGame(guild);

        //Get the message and Remove prefix from it
        String message = msg.getContentRaw().toLowerCase();
        message = message.substring(2);
        //System.out.println(message);

        //if no current game is Running on the Server
        if(currentGame == null){
            if(message.equals("start game")){
                //create Player for Host
                String hostPlayerName = msg.getAuthor().getName();
                User hostUser = msg.getAuthor();

                gameHandler.addGame(guild);
                currentGame = gameHandler.getGame(guild);
                currentGame.addHostPlayer(hostUser, hostPlayerName);
                return "You created a new Game on this server and joined as the host.";
            }
            else{
                return "Type: 'start game' to start a game.";
            }
        }
        //if a game already exists
        else{
            //
            Player player = getPlayer(msg, currentGame);

            //if the Player is not on the Server
            if(player == null){
                //check if message is 'join
                if(message.equals("join")){
                    return createPlayerOnServer(msg, currentGame);
                }
                else return "You are not in the game. Type 'join' to join the game.";
            }
            //if the player is on the Server
            else{
                //make command
                Parser parser = new Parser();
                Command command = parser.createCommand(message);

                //run command
                CommandHandler commandHandler = new CommandHandler(player, command, currentGame);
                String result = commandHandler.processCommand();
                return result;
            }
        }
    }

    private Player getPlayer(Message msg, TotalGame currentGame){
        User user = msg.getAuthor();
        return currentGame.getPlayer(user);
    }

    /**
     * create a new CoreGame.Player on the Server
     * @param msg
     * @param currentGame
     * @return
     */
    private String createPlayerOnServer(Message msg, TotalGame currentGame){
        User user = msg.getAuthor();
        String name = msg.getAuthor().getName();
        return currentGame.addPlayer(user, name);
    }
}
