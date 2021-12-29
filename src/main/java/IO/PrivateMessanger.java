package IO;

import Cards.ICard;
import CoreGame.Player;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.RestAction;



public class PrivateMessanger {

    public PrivateMessanger(){

    }
    public void sendCard(Player player, ICard card){
        sendPrivateMessage(player.getUser(), card.getDescription());
    }

    /**
     * Send a private message to the player. Returns the Message Object to be used later.
     * @param user
     * @param content
     * @return
     */
    public Message sendPrivateMessage(User user, String content) {
        RestAction<PrivateChannel> privateChannelRestAction = user.openPrivateChannel();
        PrivateChannel privateChannel = privateChannelRestAction.complete();

        return privateChannel.sendMessage(content).complete();
    }

    /**
     * Sends the player both cards that the player can select. Adds a reaction to be used.
     * @param player
     * @param card1
     * @param card2
     */
    public void sendCardSelection(Player player, ICard card1, ICard card2){
        String content = "Your two cards are: " + "\n " + "1: " +  card1.getDescription() + "\n " + "2: " + card2.getDescription();
        Message message = sendPrivateMessage(player.getUser(), content);
        addReaction(message);
    }
    public void addReaction(Message message){
        message.addReaction("1️⃣").queue();
        message.addReaction("2️⃣").queue();
    }
    public void addReaction(Message message, int count){
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
