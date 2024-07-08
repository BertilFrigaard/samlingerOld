package me.bertilfrigaard.samling.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerFiles {

    public static File file;
    public static FileConfiguration fileConfiguration;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Samling").getDataFolder(), "players.yml");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return fileConfiguration;
    }

    public static void save(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void addClaimed(String uuid, String samlingID){
        ConfigurationSection configurationSection;
        ArrayList<String> claimed = new ArrayList<>();
        if (!fileConfiguration.isConfigurationSection(uuid)){
            configurationSection = fileConfiguration.createSection(uuid);
            claimed.add(samlingID);
            configurationSection.set("claimed", claimed);
        } else {
            configurationSection = fileConfiguration.getConfigurationSection(uuid);
            claimed = (ArrayList<String>) configurationSection.get("claimed");
            claimed.add(samlingID);
            configurationSection.set("claimed",claimed);
        }

        save();
    }

    public static void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}
