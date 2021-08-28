package cc.lynzie.minigame;

import cc.lynzie.minigame.arena.GameArena;
import cc.lynzie.minigame.data.ArenaConfig;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;

public class GameManager {

  private Map<Integer, GameArena> arenas = new HashMap<>();

  private ArenaConfig arenaConfig = new ArenaConfig();

  public GameManager(JavaPlugin plugin) {
    // Initialize our config file(s), so we can use them later on.
    arenaConfig.initialize(plugin);
  }

  public GameArena createArena(String map) {
    GameArena arena = new GameArena(arenaConfig, map);
    arenas.put(arenas.size() + 1, arena);
    return arena;
  }

}