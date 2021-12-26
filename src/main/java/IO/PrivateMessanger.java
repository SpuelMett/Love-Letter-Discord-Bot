package IO;

import Cards.ICard;
import CoreGame.Player;
import net.dv8tion.jda.api.entities.User;

public class PrivateMessanger {

    public PrivateMessanger(){

    }
    public void sendCard(Player player, ICard card){
        sendPrivateMessage(player.getUser(), card.getDescription());
    }

    public void sendPrivateMessage(User user, String content) {
        // openPrivateChannel provides a RestAction<PrivateChannel>
        // which means it supplies you with the resulting channel
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).queue();
        });
    }
    public void sendCardSelection(Player player, ICard card1, ICard card2){
        String content = "Your two cards are: " + card1.getDescription() + " and " + card2.getDescription();
        sendPrivateMessage(player.getUser(), content);
    }
}
