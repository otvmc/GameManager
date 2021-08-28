package cc.lynzie.minigame.arena;

import cc.lynzie.minigame.data.ArenaConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class GameArena {

  // Basic configuration options.
  private final ArenaConfig config;
  private String arenaName;
  private String mapName;
  private int minPlayers;
  private int maxPlayers;

  // Information for the current game
  private Logger logger;
  private World arenaWorld;
  private List<Location> playerSpawns = new ArrayList<>();

  public GameArena(ArenaConfig config, String arenaName) {
    this.config = config;
    this.arenaName = arenaName;
    this.logger = LogManager.getLogger(String.format("Arena [%s]", arenaName));

    initArena();
  }

  public void initArena() {
    this.mapName = config.getConfig().getString(String.format("arenas.%s.map", arenaName));
    this.arenaWorld = Bukkit.getWorld(mapName);

    this.minPlayers = config.getConfig().getInt(String.format("arenas.%s.players.min", arenaName));
    this.maxPlayers = config.getConfig().getInt(String.format("arenas.%s.players.max", arenaName));

    for (String spawn : config.getConfig()
        .getStringList(String.format("arenas.%s.spawns", arenaName))) {
      String[] coords = spawn.split(",");
      playerSpawns.add(
          new Location(arenaWorld, Double.parseDouble(coords[0]), Double.parseDouble(coords[0]),
              Double.parseDouble(coords[0])));
    }
  }

}