package cc.lynzie.minigame.arena;

import cc.lynzie.minigame.data.ArenaConfig;
import cc.lynzie.minigame.player.GamePlayer;
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
  private String joinMessage;
  private String leaveMessage;
  private int minPlayers;
  private int maxPlayers;

  // Information for the current game
  private Logger logger;
  private World arenaWorld;
  private List<Location> playerSpawns = new ArrayList<>();
  private List<GamePlayer> players = new ArrayList<>();

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
    this.joinMessage = config.getConfig()
        .getString(String.format("arenas.%s.players.join-msg", arenaName));
    this.leaveMessage = config.getConfig()
        .getString(String.format("arenas.%s.players.leave-msg", arenaName));

    for (String spawn : config.getConfig()
        .getStringList(String.format("arenas.%s.spawns", arenaName))) {
      String[] coords = spawn.split(",");
      playerSpawns.add(
          new Location(arenaWorld, Double.parseDouble(coords[0]), Double.parseDouble(coords[0]),
              Double.parseDouble(coords[0])));
    }
  }

  public void addPlayer(GamePlayer gamePlayer) {
    players.add(gamePlayer);

    sendMessage(joinMessage.replace("{player}", gamePlayer.getDisplayName())
        .replace("{cur}", "" + players.size())
        .replace("{max}", "" + maxPlayers));
  }

  private void sendMessage(String msg) {
    for (GamePlayer player : players) {
      player.sendMessage(msg);
    }
  }

}