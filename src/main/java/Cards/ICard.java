package Cards;

import CoreGame.Game;
import CoreGame.Player;
import GameHandling.Command;

public interface ICard {

    String action(Player fromPlayer, Player onPlayer, Game game, Command command);
    int getValue();
    String getName();
    String getDescription();
    boolean isPlayOnPlayer();
    boolean isNeedsFourthWord();
}
