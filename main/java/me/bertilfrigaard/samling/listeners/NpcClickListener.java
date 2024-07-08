package me.bertilfrigaard.samling.listeners;

import me.bertilfrigaard.samling.Samling;
import me.bertilfrigaard.samling.menusystem.menus.MainMenu;
import me.bertilfrigaard.samling.utils.SamlingUtils;
import me.bertilfrigaard.samling.utils.SoundUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NpcClickListener implements Listener {

    private final Samling plugin;

    public NpcClickListener(Samling plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNpcClick(PlayerInteractEntityEvent e){
        String npcName = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("npc-name"));
        if (npcName.equals(e.getRightClicked().getCustomName())){
            Player p = e.getPlayer();

            MainMenu menu = new MainMenu(Samling.getPlayerMenuUtility(p));
            SoundUtils.playSound1(p);
            menu.open();
            return;
        }

        String loungeNPCName = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("lounge-npc-name"));
        if (loungeNPCName.equals(e.getRightClicked().getCustomName())){
            Player p = e.getPlayer();

            if (SamlingUtils.getCurPos(p.getUniqueId().toString()) <= 9) {
                p.sendMessage(" ");
                p.sendMessage("§aSender dig til " + loungeNPCName);
                p.sendMessage(" ");

                Location craftLocation = new Location(p.getWorld(), plugin.getConfig().getDouble("vip-lounge.x"), plugin.getConfig().getDouble("vip-lounge.y"), plugin.getConfig().getDouble("vip-lounge.z"));
                SoundUtils.playSound2(p);
                p.teleport(craftLocation);
            } else {
                p.sendMessage("");
                p.sendMessage("§cDu har ikke adgang til " + loungeNPCName);
                p.sendMessage("§copnå flere samlinger for at få adgang");
                p.sendMessage("");
                SoundUtils.playSound3(p);
            }
        }
    }
}
