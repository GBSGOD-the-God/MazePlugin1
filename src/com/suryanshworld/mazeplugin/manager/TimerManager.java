package com.suryanshworld.mazeplugin.manager;

import com.suryanshworld.mazeplugin.MazePlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerManager {
    private final MazePlugin plugin;
    private final Map<UUID, Long> timers;
    private final Map<String, Long> leaderboard;

    public TimerManager(MazePlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        this.plugin = plugin;
        this.timers = new HashMap<>();
        this.leaderboard = new HashMap<>();
    }

    public void startTimer(Player player) {
        UUID playerId = player.getUniqueId();
        if (!timers.containsKey(playerId)) {
            // Schedule a repeating task to update the timer every second
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    long currentTime = System.currentTimeMillis();
                    long startTime = timers.getOrDefault(playerId, currentTime);
                    long elapsedTime = currentTime - startTime;
                    long seconds = elapsedTime / 1000;

                    // Send the player a message with the current time
                    player.sendMessage("Time elapsed: " + seconds + " seconds");
                }
            };
            // Run the task every second (20 ticks)
            task.runTaskTimer(plugin, 0L, 20L);
            timers.put(playerId, System.currentTimeMillis());
        }
    }

    public void stopTimer(Player player) {
        UUID playerId = player.getUniqueId();
        timers.remove(playerId);
        player.sendMessage("Timer stopped.");
    }

    public Map<String, Long> getLeaderboard() {
        return leaderboard;
    }

    // Method to update player time on leaderboard
    public void updatePlayerTime(Player player, long timeInSeconds) {
        String playerName = player.getName();
        leaderboard.put(playerName, timeInSeconds);
    }
}
