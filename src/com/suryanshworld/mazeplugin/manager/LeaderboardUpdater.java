package com.suryanshworld.mazeplugin.manager;

import com.suryanshworld.mazeplugin.MazePlugin;
import com.suryanshworld.mazeplugin.manager.TimerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Map;

public class LeaderboardUpdater {
    private MazePlugin plugin;
    private TimerManager timerManager;
    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreboard;
    private Objective objective;

    public LeaderboardUpdater(MazePlugin plugin, TimerManager timerManager) {
        this.plugin = plugin;
        this.timerManager = timerManager;
        this.scoreboardManager = Bukkit.getScoreboardManager();
        this.scoreboard = scoreboardManager.getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("leaderboard", "dummy", ChatColor.YELLOW + "Leaderboard");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        startUpdateTask();
    }

    private void startUpdateTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateLeaderboard, 0, 600); // Update every 30 seconds (600 ticks)
    }

    private void updateLeaderboard() {
        // Clear previous entries
        scoreboard.resetScores(ChatColor.GRAY + "-------------------");

        // Fetch latest leaderboard data
        Map<String, Long> leaderboard = timerManager.getLeaderboard();

        // Add updated entries to the scoreboard
        int rank = 1;
        for (Map.Entry<String, Long> entry : leaderboard.entrySet()) {
            objective.getScore(ChatColor.YELLOW + "#" + rank + " " + entry.getKey()).setScore(rank);
            rank++;
        }
        // Update the scoreboard for all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(scoreboard);
        }
    }
}
