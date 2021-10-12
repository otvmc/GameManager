package cc.lynzie.minigame.player;

import cc.lynzie.minigame.arena.GameArena;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public abstract class GamePlayer {

  private final Player player;
  private GameArena arena;

  protected GamePlayer(Player player) {
    this.player = player;
  }

  public UUID getUniqueId() {
    return player.getUniqueId();
  }

  public void teleportPlayer(Location location) {
    player.teleportAsync(location);
  }

  public void teleportPlayer(Location location, PlayerTeleportEvent.TeleportCause cause) {
    player.teleportAsync(location, cause);
  }

  public void sendMessage(String message) {
    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
  }

  public void sendMessage(Component message) {
    player.sendMessage(message);
  }

  public void sendActionBar(Component text) {
    getPlayer().sendActionBar(text);
  }

  public String getDisplayName() {
    return player.getName();
  }

  public void setArena(GameArena gameArena) {
    this.arena = gameArena;
  }

  public Player getPlayer() {
    return player;
  }

  public GameArena getArena() {
    return arena;
  }

  public abstract void onJoin();

  public abstract void onLeave();
}
