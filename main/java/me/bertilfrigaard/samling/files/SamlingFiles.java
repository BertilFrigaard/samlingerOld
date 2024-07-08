package me.bertilfrigaard.samling.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SamlingFiles {

    public static File file;

    public static FileConfiguration fileConfiguration;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Samling").getDataFolder(), "samlinger.yml");

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

    public static void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}
