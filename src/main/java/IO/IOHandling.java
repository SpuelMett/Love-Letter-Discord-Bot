package IO;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import javax.security.auth.login.LoginException;


public class IOHandling extends ListenerAdapter {


    private final BasicInputHandling inputHandler = new BasicInputHandling();

    public static void main(String[] args) throws LoginException{

        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }

        //, GatewayIntent.GUILD_MESSAGES
        JDABuilder.createLight(args[0])
                .addEventListeners(new IOHandling())
                .setActivity(Activity.playing("Love Letter"))
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Message msg = event.getMessage();

        if(msg.getContentRaw().startsWith("!!")){
            //if from server:
            if(event.isFromGuild()){
                MessageChannel channel = event.getChannel();

                //Give message to inputHandler
                String output = inputHandler.handleInput(msg);

                channel.sendMessage(output).queue();
            }
            //if from private message
            else{
                //String output = inputHandler.handlePrivateInput(msg);
                event.getChannel().sendMessage("Du hast das Spiel verloren xD").queue();
            }

        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        //check if the reaction was made by a bot (this bot)
        if(event.getUser().isBot()) System.out.println("Bot reaction");
        else{
            //only accept Reactions from private Messages
            if(!event.isFromGuild()){

                //Message message = event.getChannel().sendMessage("").complete();
                /*
                event.getChannel().sendMessage("").queue(response -> {
                    //
                    inputHandler.handlePrivateReaction(response);

                });
                */

               inputHandler.handlePrivateReaction(event);
            }
        }
    }

    public void shutDown(){

    }
}
