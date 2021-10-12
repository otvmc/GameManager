package cc.lynzie.minigame.player;

import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GameScoreboard {

  private final Scoreboard scoreboard;
  private final Objective objective;
  private final GameScoreboardLines lines;
  private final List<Team> scoreboardTeams = new ArrayList<>();

  public GameScoreboard(Scoreboard scoreboard, Objective objective, GameScoreboardLines lines) {
    this.scoreboard = scoreboard;
    this.objective = objective;
    this.lines = lines;
  }

  public void update(GamePlayer gamePlayer) {
    objective.displayName(lines.getDisplayName());

    List<Component> lines = this.lines.getLines(gamePlayer);

    if (scoreboardTeams.isEmpty()) {
      for (int i = 0; i < lines.size(); i++) {
        int index = scoreboardTeams.size();
        int score = 15 - index;

        // Create a blank team that uses a random ChatColor as its
        // name, since that'll show as blank once applied in-game.
        String teamId = ChatColor.values()[index].toString();
        if (scoreboard.getTeam(teamId) == null) {
          Team team = scoreboard.registerNewTeam(teamId);
          team.displayName(Component.empty());
          team.addEntry(teamId);
          scoreboardTeams.add(team);
        }

        objective.getScore(teamId).setScore(score);
      }
    }

    // Go through and set all of our lines.
    for (int i = 0; i < lines.size(); i++) {
      if (scoreboardTeams.get(i) != null && lines.get(i) != null) {
        scoreboardTeams.get(i).prefix(lines.get(i));
      }
    }
  }

}