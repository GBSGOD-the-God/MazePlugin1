package com.suryanshworld.events;

import com.suryanshworld.mazeplugin.MazePlugin;
import com.suryanshworld.mazeplugin.manager.TimerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MazeEventListener implements Listener {
    private final MazePlugin plugin;
    private final TimerManager timerManager; // Add TimerManager reference
    private final Map<UUID, Long> playerTimers = new HashMap<>();

    public MazeEventListener(MazePlugin plugin, TimerManager timerManager) {
        this.plugin = plugin;
        this.timerManager = timerManager; // Initialize TimerManager
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();

        if (blockBelow.getType() == Material.LIME_WOOL) {
            startTimer(player);
        } else if (blockBelow.getType() == Material.RED_WOOL) {
            stopTimer(player);
        }
    }

    private void startTimer(Player player) {
        UUID playerId = player.getUniqueId();
        if (!playerTimers.containsKey(playerId)) {
            playerTimers.put(playerId, System.currentTimeMillis());
            player.sendMessage(ChatColor.GREEN + "Timer started!");
        }
    }

    private void stopTimer(Player player) {
        UUID playerId = player.getUniqueId();
        if (playerTimers.containsKey(playerId)) {
            long startTime = playerTimers.remove(playerId);
            long elapsedTime = System.currentTimeMillis() - startTime;
            long seconds = elapsedTime / 1000;

            player.sendMessage(ChatColor.GREEN + "Timer stopped! Time taken: " + seconds + " seconds.");

            // Update time on scoreboard
            timerManager.updatePlayerTime(player, seconds);

            // Optionally, you can also update the leaderboard here if needed
        }
    }
}
