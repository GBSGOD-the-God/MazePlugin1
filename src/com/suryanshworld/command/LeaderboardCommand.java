package com.suryanshworld.command;

import com.suryanshworld.mazeplugin.manager.TimerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderboardCommand implements CommandExecutor {
    private final TimerManager timerManager;

    public LeaderboardCommand(TimerManager timerManager) {
        this.timerManager = timerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("leaderboard")) {
            // Get the leaderboard data from the TimerManager
            Map<String, Long> leaderboard = timerManager.getLeaderboard();

            // Sort the leaderboard by completion time
            List<Map.Entry<String, Long>> sortedEntries = leaderboard.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toList());

            // Display the sorted leaderboard
            int rank = 1;
            for (Map.Entry<String, Long> entry : sortedEntries) {
                player.sendMessage(rank + ": " + entry.getKey() + " - " + entry.getValue() + " seconds");
                rank++;
            }

            return true;
        }

        return false;
    }
}
