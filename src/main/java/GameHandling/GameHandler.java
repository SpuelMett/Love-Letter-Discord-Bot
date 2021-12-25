package GameHandling;

import CoreGame.Game;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;

public class GameHandler {


    private HashMap<String, Game> gameList;

    public GameHandler(){
        gameList = new HashMap<String, Game>();
    }

    public void addGame(Guild server){
        String serverHash = server.getId();
        gameList.put(serverHash, new Game());
    }

    public Game getGame(Guild server){
        String serverHash = server.getId();
        return gameList.get(serverHash);
    }

    public void deleteGame(Guild server){
        gameList.remove(server);
    }
}
