package me.bertilfrigaard.samling.commands;

import me.bertilfrigaard.samling.Samling;
import me.bertilfrigaard.samling.files.PlayerFiles;
import me.bertilfrigaard.samling.files.SamlingFiles;
import me.bertilfrigaard.samling.menusystem.PlayerMenuUtility;
import me.bertilfrigaard.samling.menusystem.menus.AdminDisplaySamlingerMenu;
import me.bertilfrigaard.samling.menusystem.menus.NewSamlingMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminSamlingCommand implements CommandExecutor {

    public static Samling plugin;

    public AdminSamlingCommand(Samling plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length >= 1) {
            Player p = (Player) sender;
            switch (args[0]) {
                case "hjælp":
                    p.sendMessage(" ");
                    p.sendMessage("§eSamlinger admin kommandoer");
                    p.sendMessage(" ");
                    p.sendMessage("§e/asamling hjælp §7Se denne besked");
                    p.sendMessage("§e/asamling opret (navn) §7Lav en ny samling");
                    p.sendMessage("§e/asamling slet (samling-id) §7Lav en ny samling");
                    p.sendMessage("§e/asamling overblik §7Se en liste over alle samlinger og deres id");
                    p.sendMessage("§e/asamling destination §7Sæt §dVip-Lounge §7teleport destination");
                    p.sendMessage("§e/asamling reload §7Reloader alle §eSamling§7 config filer");
                    p.sendMessage(" ");
                    return true;

                case "reload":
                    SamlingFiles.reload();
                    PlayerFiles.reload();
                    plugin.reloadConfig();
                    p.sendMessage("§aConfig reloadet");
                    return true;

                case "destination":
                    Location location = p.getLocation();
                    plugin.getConfig().set("vip-lounge.x", location.getX());
                    plugin.getConfig().set("vip-lounge.y", location.getY());
                    plugin.getConfig().set("vip-lounge.z", location.getZ());
                    plugin.saveConfig();
                    p.sendMessage("§dVIP-LOUNGE §7destination sat!");
                    return true;

                case "overblik":
                    AdminDisplaySamlingerMenu adminDisplaySamlingerMenu = new AdminDisplaySamlingerMenu(Samling.getPlayerMenuUtility(p));
                    adminDisplaySamlingerMenu.open();
                    return true;

                case "slet":
                    if (args.length >= 2) {
                        StringBuilder builder = new StringBuilder();

                        for (int i = 1; i < args.length; i++) {
                            builder.append(args[i]);
                            builder.append("");
                        }

                        String samlingID = builder.toString().trim();

                        if (SamlingFiles.get().isConfigurationSection(samlingID)){
                            p.sendMessage("§a§lSucces §7Samlingen §f" + SamlingFiles.fileConfiguration.get(samlingID + ".name") + " §7er nu slettet");
                            for (String uuid : SamlingFiles.get().getStringList(samlingID + ".claimed-by")){
                                List<String> current = PlayerFiles.get().getStringList(uuid + ".claimed");
                                current.remove(samlingID);
                                PlayerFiles.get().set(uuid + ".claimed", current);
                            }

                            SamlingFiles.get().set(samlingID, null);
                            SamlingFiles.save();
                            PlayerFiles.save();
                        } else {
                            p.sendMessage("§c§lFEJL §7Kunne ikke finde samling med Id: §f" + samlingID + " §7brug §c/asamling overblik §7for at finde en samlings ID");
                        }
                        return true;

                    }
                    return false;

                case "opret":
                    if (args.length >= 2) {
                        StringBuilder builder = new StringBuilder();

                        for (int i = 1; i < args.length; i++) {
                            builder.append(args[i]);
                            builder.append(" ");
                        }

                        String samlingName = builder.toString().trim() + ChatColor.RESET;

                        if (samlingName.length() < plugin.getConfig().getInt("samling-maks-len")) {

                            PlayerMenuUtility playerMenuUtility = Samling.getPlayerMenuUtility(p);
                            playerMenuUtility.setNewSamlingName(ChatColor.translateAlternateColorCodes('&',samlingName));
                            NewSamlingMenu menu = new NewSamlingMenu(playerMenuUtility);
                            menu.open();
                        } else {
                            p.sendMessage("§c§lFejl! §cNavn på samling var for langt. Kan ændres i §econfig.yml");
                        }
                    } else {
                        p.sendMessage("§c§lFejl! §e/asamling opret (navn)");
                    }
                    return true;
            }
        }
        return false;
    }
}
