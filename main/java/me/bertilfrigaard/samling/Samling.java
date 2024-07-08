package me.bertilfrigaard.samling;

import me.bertilfrigaard.samling.commands.AdminSamlingCommand;
import me.bertilfrigaard.samling.commands.SamlingCommand;
import me.bertilfrigaard.samling.files.PlayerFiles;
import me.bertilfrigaard.samling.files.SamlingFiles;
import me.bertilfrigaard.samling.listeners.MenuListener;
import me.bertilfrigaard.samling.listeners.NpcClickListener;
import me.bertilfrigaard.samling.menusystem.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Samling extends JavaPlugin {

    public static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Setup configs
        saveDefaultConfig();
        SamlingFiles.setup();
        PlayerFiles.setup();

        //Setup commands
        getCommand("samling").setExecutor(new SamlingCommand(this));
        getCommand("adminsamling").setExecutor(new AdminSamlingCommand(this));

        //Setup listeners
        getServer().getPluginManager().registerEvents(new MenuListener(), this);;
        getServer().getPluginManager().registerEvents(new NpcClickListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p){

        if(playerMenuUtilityMap.containsKey(p)) {
            return playerMenuUtilityMap.get(p);
        }

        PlayerMenuUtility playerMenuUtility = new PlayerMenuUtility(p);
        playerMenuUtilityMap.put(p,playerMenuUtility);
        return playerMenuUtility;

    }
}
