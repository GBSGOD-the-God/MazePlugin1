package com.suryanshworld.command;

import com.suryanshworld.mazeplugin.MazePlugin;
import com.suryanshworld.mazeplugin.manager.MazeWorldManager;
import com.suryanshworld.mazeplugin.manager.TimerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MazeCommand implements CommandExecutor {

    private final MazePlugin plugin;
    private final MazeWorldManager mazeWorldManager;
    private final TimerManager timerManager;

    public MazeCommand(MazePlugin plugin, MazeWorldManager mazeWorldManager, TimerManager timerManager) {
        this.plugin = plugin;
        this.mazeWorldManager = mazeWorldManager;
        this.timerManager = timerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // Initialize the maze entrance location if it hasn't been initialized already
        if (mazeWorldManager.getMazeEntranceLocation() == null) {
            mazeWorldManager.setMazeEntranceLocation(player.getLocation());
        }

        // Teleport the player to the maze entrance location
        mazeWorldManager.teleportToMaze(player);
        return true;
    }
}
