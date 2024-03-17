package com.suryanshworld.mazeplugin;

import com.suryanshworld.events.MazeEventListener;
import com.suryanshworld.command.LeaderboardCommand;
import com.suryanshworld.command.MazeCommand;
import com.suryanshworld.mazeplugin.manager.MazeWorldManager;
import com.suryanshworld.mazeplugin.manager.ScoreboardManager;
import com.suryanshworld.mazeplugin.manager.TimerManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MazePlugin extends JavaPlugin {
    private MazeWorldManager mazeWorldManager;
    private TimerManager timerManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        mazeWorldManager = new MazeWorldManager(this);
         timerManager = new TimerManager(this);
        scoreboardManager = new ScoreboardManager();

        // Register the event listener
        getServer().getPluginManager().registerEvents(new MazeEventListener(this, timerManager), this);

        // Register commands
        getCommand("startmaze").setExecutor(new MazeCommand(this, mazeWorldManager, timerManager));
        getCommand("leaderboard").setExecutor(new LeaderboardCommand(timerManager));
    }

    // Method to get the scoreboard manager
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
