package cc.lynzie.minigame;

import cc.lynzie.minigame.arena.GameArena;
import cc.lynzie.minigame.commands.StateCommand;
import cc.lynzie.minigame.data.ArenaConfig;
import cc.lynzie.minigame.player.GamePlayer;
import co.aikar.commands.PaperCommandManager;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GameManager {

  private JavaPlugin javaPlugin;
  private PaperCommandManager commandManager;
  private Map<Integer, GameArena> arenas = new HashMap<>();

  private ArenaConfig arenaConfig = new ArenaConfig();

  public GameManager(JavaPlugin javaPlugin) {
    this.javaPlugin = javaPlugin;
    this.commandManager = new PaperCommandManager(javaPlugin);

    // Register our commands.
    commandManager.registerCommand(new StateCommand(this));

    // Initialize our config file(s), so we can use them later on.
    arenaConfig.initialize(javaPlugin);
  }

  public GameArena createArena(String map) {
    GameArena arena = new GameArena(this, map);
    arenas.put(arenas.size() + 1, arena);
    return arena;
  }

  public GameArena createArena() {
    // TODO: Pick random map.
    return createArena("test");
  }

  public GameArena getOpenArena() {
    for (GameArena arena : arenas.values()) {
      if (arena.isAllowingNewPlayers()) return arena;
    }

    return createArena("test");
  }

  public GameArena findArenaByPlayer(Player player) {
    for (GameArena arena : arenas.values()) {
      for (GamePlayer arenaPlayer : arena.getPlayers()) {
        if (arenaPlayer.getUniqueId() == player.getUniqueId()) return arena;
      }
    }

    return null;
  }

  public JavaPlugin getJavaPlugin() {
    return javaPlugin;
  }

  public ArenaConfig getArenaConfig() {
    return arenaConfig;
  }
}