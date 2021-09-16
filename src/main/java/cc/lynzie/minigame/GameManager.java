package cc.lynzie.minigame;

import cc.lynzie.minigame.arena.GameArena;
import cc.lynzie.minigame.commands.StateCommand;
import cc.lynzie.minigame.player.GamePlayer;
import co.aikar.commands.PaperCommandManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GameManager {

  private JavaPlugin javaPlugin;
  private PaperCommandManager commandManager;

  private Map<UUID, GamePlayer> gamePlayersByUuid = new HashMap<>();
  private Map<Integer, GameArena> arenas = new HashMap<>();

  public GameManager(JavaPlugin javaPlugin) {
    this.javaPlugin = javaPlugin;
    this.commandManager = new PaperCommandManager(javaPlugin);

    // Register our commands.
    commandManager.registerCommand(new StateCommand(this));
  }

  public GameArena createArena(Location gameArena, int minPlayers, int maxPlayers) {
    GameArena arena = new GameArena(this, gameArena, minPlayers, maxPlayers);
    arenas.put(arenas.size() + 1, arena);
    return arena;
  }

  /*public GameArena createArena() {
    // TODO: Pick random map.
    return createArena("test");
  }

  public GameArena getOpenArena() {
    for (GameArena arena : arenas.values()) {
      if (arena.isAllowingNewPlayers()) {
        return arena;
      }
    }

    return createArena();
  }*/

  public GameArena findArenaByPlayer(Player player) {
    for (GameArena arena : arenas.values()) {
      for (GamePlayer arenaPlayer : arena.getPlayers()) {
        if (arenaPlayer.getUniqueId() == player.getUniqueId()) {
          return arena;
        }
      }
    }

    return null;
  }

  public GamePlayer getPlayer(UUID uuid) {
    return gamePlayersByUuid.get(uuid);
  }

  public GamePlayer getPlayer(Player player) {
    return gamePlayersByUuid.get(player.getUniqueId());
  }

  public void addPlayer(GamePlayer gamePlayer) {
    gamePlayersByUuid.putIfAbsent(gamePlayer.getUniqueId(), gamePlayer);
  }

  public JavaPlugin getJavaPlugin() {
    return javaPlugin;
  }

}