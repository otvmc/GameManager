package cc.lynzie.minigame.arena.state;

import cc.lynzie.minigame.arena.GameArena;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class GameState implements Listener {

  public GameState(JavaPlugin javaPlugin, GameArena gameArena) {
    this.javaPlugin = javaPlugin;
    this.arena = gameArena;
  }

  private JavaPlugin javaPlugin;
  private GameArena arena;
  private Instant startTime;
  private Duration stateDuration;
  private boolean started;
  private boolean ended;
  private boolean frozen;

  public void start() {
    // Set the start info for the state.
    started = true;
    startTime = Instant.now();
    javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);

    // Perform the tasks the user has specified.
    stateStart();
  }

  public void end() {
    ended = true;

    HandlerList.unregisterAll(this);
    arena.getGameStates().remove(this);
    // Perform the tasks the user has specified.
    stateEnd();
  }

  public void update() {
    // If the state is ready to end and an admin hasn't frozen it
    // in place then proceed to ending the state
    if (isAbleToEnd() && !frozen) {
      end();
      return;
    }

    javaPlugin.getServer().getScheduler().runTask(javaPlugin, stateUpdate());
  }

  public boolean isAbleToEnd() {
    // Make sure the state hasn't ended, and that the time remaining is
    // zero so that it doesn't end sooner.
    return ended || getRemaining() == Duration.ZERO;
  }

  public abstract void stateStart();

  public abstract Runnable stateUpdate();

  public abstract void stateEnd();

  public boolean isStarted() {
    return started;
  }

  public boolean isEnded() {
    return ended;
  }

  public boolean isFrozen() {
    return frozen;
  }

  public void setStateDuration(Duration stateDuration) {
    this.stateDuration = stateDuration;
  }

  public void setFrozen(boolean frozen) {
    this.frozen = frozen;
  }

  public GameArena getArena() {
    return arena;
  }

  public Instant getStartTime() {
    return startTime;
  }

  public Duration getStateDuration() {
    return stateDuration;
  }

  public Duration getRemaining() {
    // Calculate how long is left on the state, by getting the duration between
    // now and the start time, then subtracting the max duration.
    Duration startToNow = Duration.between(startTime, Instant.now());
    Duration remainingDuration = stateDuration.minus(startToNow);

    // If an admin has frozen the state then it can go under, if
    // this happens then just return zero - else return the actual
    // duration of the state.
    if (remainingDuration.isNegative()) {
      return Duration.ZERO;
    }
    return remainingDuration;
  }

  /**
   * Calculates how much time is remaining and returns it as
   * @return How long the state will last for in an array, [0] is minutes and [1] is seconds.
   */
  public int[] getRemainingTime() {
    Duration remaining = getRemaining();
    return new int[]{remaining.toMinutesPart(), remaining.toSecondsPart()};
  }

}