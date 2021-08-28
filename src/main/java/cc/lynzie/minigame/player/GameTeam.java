package cc.lynzie.minigame.player;

import java.util.ArrayList;
import java.util.List;

public class GameTeam {

    private List<GamePlayer> teamPlayers = new ArrayList<>();

    public void sendMessage(String message) {
        for (GamePlayer player : teamPlayers) {
            player.sendMessage(message);
        }
    }

}