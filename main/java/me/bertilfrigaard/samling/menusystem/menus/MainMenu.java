package me.bertilfrigaard.samling.menusystem.menus;

import me.bertilfrigaard.samling.files.PlayerFiles;
import me.bertilfrigaard.samling.menusystem.Menu;
import me.bertilfrigaard.samling.menusystem.PlayerMenuUtility;
import me.bertilfrigaard.samling.utils.MenuUtils;
import me.bertilfrigaard.samling.utils.SamlingUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Menu {
    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lSAMLINGER";
    }

    @Override
    public int setSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getSlot() == 22){
            Player p = (Player) e.getWhoClicked();

            p.closeInventory();

            DisplaySamlingerMenu menu = new DisplaySamlingerMenu(playerMenuUtility);
            menu.open();

        }
        if (e.getSlot() == 24){
            Player p = (Player) e.getWhoClicked();

            p.closeInventory();

            LeaderboardMenu menu = new LeaderboardMenu(playerMenuUtility);
            menu.open();

        }
    }

    @Override
    public void setMenuItems() {

        //Pynt
        ItemStack yellowGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        ItemMeta yellowGlassPaneMeta = yellowGlassPane.getItemMeta();
        yellowGlassPaneMeta.setDisplayName(" ");
        yellowGlassPane.setItemMeta(yellowGlassPaneMeta);

        ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        ItemMeta orangeGlassPaneMeta = orangeGlassPane.getItemMeta();
        orangeGlassPaneMeta.setDisplayName(" ");
        orangeGlassPane.setItemMeta(orangeGlassPaneMeta);

        MenuUtils.fillSlotsArray(inventory, yellowGlassPane, new int[]{0, 2, 4, 6, 8, 37, 39, 41, 43});
        MenuUtils.fillSlotsArray(inventory, orangeGlassPane, new int[]{1, 3, 5, 7, 36, 38, 40, 42, 44});

        //Knapper
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&3&lSAMLINGER"));
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.translateAlternateColorCodes('&',"&7Tryk her for at se eller klare"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&7de nuværende samlinger"));
        lore.add(" ");

        int claimedCount = 0;

        if (PlayerFiles.get().isConfigurationSection(playerMenuUtility.getOwner().getUniqueId().toString())){
            claimedCount = PlayerFiles.get().getList(playerMenuUtility.getOwner().getUniqueId().toString() + ".claimed").size();
        }

        lore.add(ChatColor.translateAlternateColorCodes('&',"&8【&7Dine Samlinger: &f" + claimedCount + "&8】"));
        chestMeta.setLore(lore);
        chest.setItemMeta(chestMeta);

        
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&5&lLEADERBOARD"));
        List<String> swordLore = new ArrayList<>();
        swordLore.add(" ");
        swordLore.add(ChatColor.translateAlternateColorCodes('&',"&7Tryk her for at se hvem"));
        swordLore.add(ChatColor.translateAlternateColorCodes('&',"&7der har klaret flest samlinger"));
        swordLore.add(" ");
        swordLore.add(ChatColor.translateAlternateColorCodes('&',"&8【&7Din Placering: &f" + SamlingUtils.getCurPos(playerMenuUtility.getOwner().getUniqueId().toString()) + "&8】"));
        swordMeta.setLore(swordLore);
        sword.setItemMeta(swordMeta);

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&c&lINFORMATION"));
        List<String> paperLore = new ArrayList<>();
        paperLore.add(" ");
        paperLore.add(ChatColor.translateAlternateColorCodes('&',"&7Samlinger er små kollektioner"));
        paperLore.add(ChatColor.translateAlternateColorCodes('&',"&7af items som kan samles"));
        paperLore.add(" ");
        paperLore.add(ChatColor.translateAlternateColorCodes('&',"&7Du opnår fordele ved at"));
        paperLore.add(ChatColor.translateAlternateColorCodes('&',"&7samle mange samlinger"));
        paperLore.add(" ");
        paperLore.add(ChatColor.translateAlternateColorCodes('&',"&7Der er også et &eVIP-OMRÅDE"));
        paperLore.add(ChatColor.translateAlternateColorCodes('&',"&7som de spillere med flest samlinger"));
        paperLore.add(ChatColor.translateAlternateColorCodes('&',"&7har adgang til"));
        paperMeta.setLore(paperLore);
        paper.setItemMeta(paperMeta);

        inventory.setItem(20,paper);
        inventory.setItem(22,chest);
        inventory.setItem(24,sword);

    }
}
