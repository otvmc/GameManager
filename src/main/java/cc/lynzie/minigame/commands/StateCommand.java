package cc.lynzie.minigame.commands;

import cc.lynzie.minigame.GameManager;
import cc.lynzie.minigame.arena.GameArena;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

@CommandAlias("state|game|gamestate")
public class StateCommand extends BaseCommand {

  private static GameManager gameManager;

  public StateCommand(GameManager gameManager) {
    StateCommand.gameManager = gameManager;
  }

  @Subcommand("skip")
  public static void skipState(Player player) {
    GameArena arena = gameManager.findArenaByPlayer(player);
    if (arena == null) {
      player.sendMessage(Component.text("You need to be in a game to use this!")
          .color(TextColor.color(255, 80, 80)));
      return;
    }

    player.sendMessage(Component.text("Skipping " + arena.getCurrentGameState().getClass().getSimpleName())
        .decorate(TextDecoration.ITALIC).color(TextColor.color(245, 245, 245)));
    arena.getCurrentGameState().skip();
  }

  @Subcommand("freeze")
  public static void freezeState(Player player) {
    GameArena arena = gameManager.findArenaByPlayer(player);
    if (arena == null) {
      player.sendMessage(Component.text("You need to be in a game to use this!")
          .color(TextColor.color(255, 80, 80)));
      return;
    }

    arena.getCurrentGameState().setFrozen(!arena.getCurrentGameState().isFrozen());
  }

}