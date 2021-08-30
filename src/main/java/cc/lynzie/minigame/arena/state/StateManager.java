package cc.lynzie.minigame.arena.state;

import cc.lynzie.minigame.GameManager;
import cc.lynzie.minigame.arena.GameArena;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class StateManager {

  private final GameArena gameArena;

  public StateManager(GameArena gameArena) {
    this.gameArena = gameArena;
  }

  /**
   * Runs {@link GameState#update()} every second, and starts up a state
   * if there's a new one to be started.
   */
  public void performUpdate() {
    GameState currentGameState = gameArena.getCurrentGameState();

    // If the state has ended, switch it over to the next one and start it.
    if (currentGameState.isEnded() && !gameArena.getGameStates().isEmpty()) {
      gameArena.setCurrentGameState(gameArena.getGameStates().get(0));
      currentGameState = gameArena.getCurrentGameState();
      currentGameState.start();
    }

    if (currentGameState.stateUpdate() == null) return;
    currentGameState.update();
  }

}