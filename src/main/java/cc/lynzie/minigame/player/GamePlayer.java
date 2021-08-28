package cc.lynzie.minigame.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class GamePlayer {

    private final Player player;

    protected GamePlayer(Player player) {
        this.player = player;
    }

    public void teleportPlayer(Location location) {
        player.teleport(location);
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }
}
