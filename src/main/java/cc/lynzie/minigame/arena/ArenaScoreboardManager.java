package cc.lynzie.minigame.arena;

import cc.lynzie.minigame.GameManager;
import cc.lynzie.minigame.player.GamePlayer;
import cc.lynzie.minigame.player.GameScoreboard;
import cc.lynzie.minigame.player.GameScoreboardLines;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ArenaScoreboardManager {

  private final Map<UUID, GameScoreboard> scoreboardsForPlayer = new HashMap<>();
  private final GameManager gameManager;

  public ArenaScoreboardManager(GameManager gameManager) {
    this.gameManager = gameManager;
  }

  public GameScoreboard createBoardForPlayer(GamePlayer player, GameScoreboardLines lines) {
    if (scoreboardsForPlayer.containsKey(player.getUniqueId())) {
      return scoreboardsForPlayer.get(player.getUniqueId());
    }

    // Create our scoreboard and register the objective.
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    scoreboard.registerNewObjective("GameBoard", "dummy", lines.getDisplayName());
    Objective gameBoard = scoreboard.getObjective("GameBoard");
    gameBoard.setDisplaySlot(DisplaySlot.SIDEBAR);

    // Create our GameScoreboard, provide it our lines and put it into the list.
    GameScoreboard gameScoreboard = new GameScoreboard(scoreboard, gameBoard,
        lines);
    Bukkit.getPlayer(player.getUniqueId()).setScoreboard(scoreboard);
    scoreboardsForPlayer.put(player.getUniqueId(), gameScoreboard);

    return gameScoreboard;
  }

  public void updateBoards() {
    scoreboardsForPlayer.forEach((uuid, scoreboard) -> {
      Player player = Bukkit.getPlayer(uuid);
      if (player == null || !player.isOnline()) {
        scoreboardsForPlayer.remove(uuid);
        return;
      }

      scoreboard.update(gameManager.getPlayer(uuid));
    });
  }

}