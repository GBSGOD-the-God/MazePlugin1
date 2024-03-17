package com.suryanshworld.mazeplugin.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    private static final String FILE_PATH = "plugins/MazePlugin/leaderboard.txt";

    public static Map<String, Long> readLeaderboardFromFile() {
        Map<String, Long> leaderboard = new HashMap<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String playerName = parts[0];
                        long completionTime = Long.parseLong(parts[1]);
                        leaderboard.put(playerName, completionTime);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return leaderboard;
    }

    public static void writeLeaderboardToFile(Map<String, Long> leaderboard) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, Long> entry : leaderboard.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
