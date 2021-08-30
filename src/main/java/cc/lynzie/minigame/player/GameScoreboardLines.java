package cc.lynzie.minigame.player;

import java.util.List;
import net.kyori.adventure.text.Component;

public interface GameScoreboardLines {

  Component getDisplayName();

  List<Component> getLines(GamePlayer gamePlayer);

}