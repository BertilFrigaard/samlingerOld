package me.bertilfrigaard.samling.commands;

import me.bertilfrigaard.samling.Samling;
import me.bertilfrigaard.samling.menusystem.menus.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SamlingCommand implements CommandExecutor {

    public final Samling plugin;

    public SamlingCommand(Samling plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!plugin.getConfig().getBoolean("enable-samling-command")) return false;

        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;

        MainMenu menu = new MainMenu(Samling.getPlayerMenuUtility(p));
        menu.open();

        return true;
    }
}
