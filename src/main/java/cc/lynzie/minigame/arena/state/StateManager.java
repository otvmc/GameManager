package cc.lynzie.minigame.arena.state;

import cc.lynzie.minigame.arena.GameArena;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StateManager {

  private final GameArena gameArena;
  private Logger logger = LogManager.getLogger("StateManager");

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

    try {
      currentGameState.update();
      gameArena.getScoreboardManager().updateBoards();
    } catch (Exception ex) {
      logger.warn("There was an exception while handling the state {}! Please notify the author of {}, and let them know:",
          currentGameState.getClass().getSimpleName(), gameArena.getGameManager().getJavaPlugin().getName(), ex);
    }
  }

}