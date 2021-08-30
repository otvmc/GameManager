package cc.lynzie.minigame.player;

import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class GamePlayer {

  private final Player player;

  protected GamePlayer(Player player) {
    this.player = player;
  }

  public UUID getUniqueId() {
    return player.getUniqueId();
  }

  public void teleportPlayer(Location location) {
    player.teleport(location);
  }

  public void sendMessage(String message) {
    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
  }

  public void sendMessage(Component message) {
    player.sendMessage(message);
  }

  public String getDisplayName() {
    return player.getName();
  }

  protected Player getPlayer() {
    return player;
  }

  public abstract void onJoin();

  public abstract void onLeave();

}
