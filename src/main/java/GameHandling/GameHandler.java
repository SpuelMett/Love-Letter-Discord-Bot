package GameHandling;

import CoreGame.Game;
import CoreGame.Player;
import CoreGame.TotalGame;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;

public class GameHandler {


    private HashMap<String, TotalGame> gameList;

    public GameHandler(){
        gameList = new HashMap<String, TotalGame>();
    }

    public void addGame(Guild server){
        String serverHash = server.getId();
        gameList.put(serverHash, new TotalGame());
    }

    public TotalGame getGame(Guild server){
        String serverHash = server.getId();
        return gameList.get(serverHash);
    }

    public void deleteGame(Guild server){
        gameList.remove(server);
    }

    public TotalGame findPlayer(User user){
        for(TotalGame totalGame: gameList.values()){
            if (totalGame.getPlayer(user) != null) {
                return totalGame;
            }
        }
        return null;
    }
}
