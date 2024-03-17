package com.suryanshworld.mazeplugin.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    private Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardManager() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("Time", "dummy", ChatColor.YELLOW + "Time");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateTime(Player player, long timeInSeconds) {
        if (player != null && player.isOnline()) {
            objective.getScore(ChatColor.GREEN + "Time:").setScore((int) timeInSeconds);
            player.setScoreboard(scoreboard);
        }
    }
}
