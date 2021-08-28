package cc.lynzie.minigame;

import cc.lynzie.minigame.data.ArenaConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class GameManager {

    private ArenaConfig arenaConfig = new ArenaConfig();

    public GameManager() {
        // Initialize our config file(s), so we can use them later on.
        arenaConfig.initialize();
    }

}