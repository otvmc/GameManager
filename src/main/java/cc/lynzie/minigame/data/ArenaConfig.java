package cc.lynzie.minigame.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ArenaConfig {

    private File file;
    private FileConfiguration config;
    private Logger logger = LogManager.getLogManager().getLogger(getClass().getName());

    public void initialize() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("GameManager").getDataFolder(), "arena.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                logger.info("Successfully created arena.yml! Make sure to edit it as per your arena setup!");
            } catch (Exception ex) {
                logger.warning("Couldn't create arena.yml! Are your permissions setup correctly?");
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
            logger.info("Successfully saved arena.yml!");
        } catch (Exception ex) {
            logger.warning("Couldn't save arena.yml! Are your permissions setup correctly?");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
