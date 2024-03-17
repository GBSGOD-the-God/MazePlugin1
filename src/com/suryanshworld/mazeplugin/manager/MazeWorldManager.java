package com.suryanshworld.mazeplugin.manager;

import com.suryanshworld.mazeplugin.MazePlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.io.File;

public class MazeWorldManager {
    private MazePlugin plugin;
    private World mazeWorld;
    private Location entranceLocation;

    public MazeWorldManager(MazePlugin plugin) {
        this.plugin = plugin;
        createMazeWorld();
        initializeEntranceLocation();
    }

    private void createMazeWorld() {
        File worldFolder = new File(plugin.getServer().getWorldContainer(), "maze_world");
        if (!worldFolder.exists()) {
            worldFolder.mkdirs();
        }

        WorldCreator creator = new WorldCreator("maze_world");
        mazeWorld = creator.createWorld();
        mazeWorld.setGameRuleValue("doMobSpawning", "false");
        mazeWorld.setGameRuleValue("doDaylightCycle", "false");
        mazeWorld.setGameRuleValue("keepInventory", "true");
        mazeWorld.setDifficulty(org.bukkit.Difficulty.PEACEFUL);
    }

    private void initializeEntranceLocation() {
        // Set entrance location as a specific position in the maze world
        entranceLocation = new Location(mazeWorld, 0, 64, 0);
    }

    public void teleportToMaze(Player player) {
        if (entranceLocation != null) {
            player.teleport(entranceLocation);
            // Show title "Hello! Welcome to the Maze" to the player
            player.sendTitle(ChatColor.GREEN + "Hello!", ChatColor.YELLOW + "Welcome to the Maze, " + ChatColor.RED + "DUMBO", 10, 70, 20);
        } else {
            plugin.getLogger().warning("Entrance location is not initialized!");
        }
    }

    // Method to set the maze entrance location
    public void setMazeEntranceLocation(Location location) {
        this.entranceLocation = location;
    }

    // Method to get the maze entrance location
    public Location getMazeEntranceLocation() {
        return entranceLocation;
    }
}
