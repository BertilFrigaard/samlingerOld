package me.bertilfrigaard.samling.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SoundUtils {
    public static void playSound1(Player p){
        if (Bukkit.getPluginManager().getPlugin("Samling").getConfig().getBoolean("sounds-enabled")){
            p.playSound(p.getLocation(), Bukkit.getPluginManager().getPlugin("Samling").getConfig().getString("sound-click"), 0.5F, 1.2F);
        }
    }
    public static void playSound2(Player p){
        if (Bukkit.getPluginManager().getPlugin("Samling").getConfig().getBoolean("sounds-enabled")) {
            p.playSound(p.getLocation(), Bukkit.getPluginManager().getPlugin("Samling").getConfig().getString("sound-yes"), 0.8F, 1.0F);
        }
    }
    public static void playSound3(Player p){
        if (Bukkit.getPluginManager().getPlugin("Samling").getConfig().getBoolean("sounds-enabled")) {
            p.playSound(p.getLocation(), Bukkit.getPluginManager().getPlugin("Samling").getConfig().getString("sound-no"), 0.6F, 1.4F);
        }
    }
}
