package IO;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class PublicMessenger {

    public PublicMessenger(){

    }

    public Message sendPublicMessage(MessageChannel channel, String content){
        return channel.sendMessage(content).complete();
    }

    public void addReaction(Message message,int count){
        if(count >= 1) message.addReaction("1️⃣").queue();
        if(count >= 2) message.addReaction("2️⃣").queue();
        if(count >= 3) message.addReaction("3️⃣").queue();
        if(count >= 4) message.addReaction("4️⃣").queue();
        if(count >= 5) message.addReaction("5️⃣").queue();
        if(count >= 6) message.addReaction("6️⃣").queue();
        if(count >= 7) message.addReaction("7️⃣").queue();
        if(count >= 8) message.addReaction("8️⃣").queue();
        if(count >= 9) message.addReaction("9️⃣").queue();
    }
}
