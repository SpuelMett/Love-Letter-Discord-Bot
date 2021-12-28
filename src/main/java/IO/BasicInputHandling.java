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
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

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
                currentGame.setChannel(msg.getChannel());
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

    /**
     * Returns the private Message
     * @param event
     * @return
     */
    public String handlePrivateReaction(MessageReactionAddEvent event){
        //check if any Game exists with this server
        User user = event.getUser();
        TotalGame currentGame = gameHandler.findPlayer(user);
        if(currentGame == null){
            return "You don't play this game.";
        }
        else{
            Player player = currentGame.getPlayer(user);
            String name = event.getReactionEmote().getEmoji();
            int nr = emojiToInt(name);
            if(nr == 0) return "This is not a valid reaction!";

            currentGame.reactionPlayCard(player, nr);
            return "";
        }
    }
    public void handlePublicReaction(MessageReactionAddEvent event){
        System.out.println("Test3");
        User user = event.getUser();
        TotalGame currentGame = gameHandler.findPlayer(user);
        if(currentGame == null){
            //do nothing
            System.out.println("Test4");
        }
        else{
            Player player = currentGame.getPlayer(user);
            String name = event.getReactionEmote().getEmoji();
            int nr = emojiToInt(name);
            if(nr == 0) return;

            currentGame.reactionAnyCard(player, nr);
        }
    }

    private int emojiToInt(String emoji){
        if(emoji.equals("1️⃣")) return 1;
        if(emoji.equals("2️⃣")) return 2;
        if(emoji.equals("3️⃣")) return 3;
        if(emoji.equals("4️⃣")) return 4;
        if(emoji.equals("5️⃣")) return 5;
        if(emoji.equals("6️⃣")) return 6;
        if(emoji.equals("7️⃣")) return 7;
        if(emoji.equals("8️⃣")) return 8;
        return 0;
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
