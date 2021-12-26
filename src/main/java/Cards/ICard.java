package Cards;

import CoreGame.Game;
import CoreGame.Player;

public interface ICard {

    String action(Player fromPlayer, Player onPlayer, Game game);
    int getValue();
    String getName();
    boolean isPlayOnPlayer();
}
