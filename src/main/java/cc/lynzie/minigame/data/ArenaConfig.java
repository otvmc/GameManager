package cc.lynzie.minigame.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class ArenaConfig {

  private File file;
  private FileConfiguration config;
  private Logger logger = Bukkit.getLogger();

  public void initialize(JavaPlugin plugin) {
    file = new File(Bukkit.getServer().getPluginManager().getPlugin("GameManager").getDataFolder(),
        "arena.yml");
    plugin.saveResource("arena.yml", false);
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
