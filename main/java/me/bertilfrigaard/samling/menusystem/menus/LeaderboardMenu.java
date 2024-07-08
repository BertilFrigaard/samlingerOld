package me.bertilfrigaard.samling.menusystem.menus;

import me.bertilfrigaard.samling.menusystem.Menu;
import me.bertilfrigaard.samling.menusystem.PlayerMenuUtility;
import me.bertilfrigaard.samling.utils.MenuUtils;
import me.bertilfrigaard.samling.utils.SamlingUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LeaderboardMenu extends Menu {
    public LeaderboardMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§5§lLEADERBOARD";
    }

    @Override
    public int setSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getSlot() == 48) {
            e.getWhoClicked().closeInventory();
            MainMenu menu = new MainMenu(playerMenuUtility);
            menu.open();
        }
    }

    @Override
    public void setMenuItems() {

        ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        ItemMeta orangeGlassPaneMeta = orangeGlassPane.getItemMeta();
        orangeGlassPaneMeta.setDisplayName(" ");
        orangeGlassPane.setItemMeta(orangeGlassPaneMeta);

        ItemStack lightBlueGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
        ItemMeta lightBlueGlassPaneMeta = lightBlueGlassPane.getItemMeta();
        lightBlueGlassPaneMeta.setDisplayName(" ");
        lightBlueGlassPane.setItemMeta(lightBlueGlassPaneMeta);

        MenuUtils.fillSlotsArray(inventory, orangeGlassPane, new int[]{45,46,47,49,51,52,53,0,8,9,17,18,26,27,35,36,44});

        MenuUtils.fillSlotsArray(inventory, lightBlueGlassPane, new int[]{1,2,3,4,5,6,7,10,11,12,14,15,16,19,20,24,25,28,34,37,38,39,40,41,42,43});

        //Return button
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName("§c§lTILBAGE");
        List<String> lore = new ArrayList<>();
        lore.add("§7Tryk her for at gå tilbage");
        lore.add("§7til den forrige side");
        barrierMeta.setLore(lore);
        barrier.setItemMeta(barrierMeta);

        //Book
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        bookMeta.setDisplayName("§6§lDIN PLACERING");
        List<String> bookLore = new ArrayList<>();
        bookLore.add("");
        bookLore.add("§7Din nuværende placering:");
        bookLore.add("§8【§7Placering: §f" + SamlingUtils.getCurPos(playerMenuUtility.getOwner().getUniqueId().toString()) + "§8】");
        bookLore.add("");
        bookLore.add("§7Adgang til vip-lounge:");

        String access = "§c§l✘";
        if (SamlingUtils.getCurPos(playerMenuUtility.getOwner().getUniqueId().toString()) <= 9){
            access = "§a§l✔";
        }

        bookLore.add("§8【§7Adgang: " + access + "§8】"); ///news add &c&l❌❌❌✅✅✅✘ ✗ &a&l✔ ✓
        bookMeta.setLore(bookLore);
        book.setItemMeta(bookMeta);

        inventory.setItem(48,barrier);
        inventory.setItem(50,book);

        //Set heads
        ItemStack defaultHead = new ItemStack(Material.SKULL_ITEM);
        ItemMeta defaultHeadMeta = defaultHead.getItemMeta();
        defaultHeadMeta.setDisplayName("§cIngen");
        ArrayList<String> defaultHeadLore = new ArrayList<>();
        defaultHeadLore.add("§7Ingen spiller har taget");
        defaultHeadLore.add("§7denne plads endu");
        defaultHeadMeta.setLore(defaultHeadLore);
        defaultHead.setItemMeta(defaultHeadMeta);

        List<Map.Entry<String, Integer>> rankedPlayers = SamlingUtils.getTop();

        int[] guiPos = {13,21,22,23,29,30,31,32,33};

        String[] guiColor = Bukkit.getServer().getPluginManager().getPlugin("Samling").getConfig().getString("leaderboard-colors").split(",");

        for (int i = 0; i < 9; i++) {
            if (rankedPlayers.size() <= i) {
                inventory.setItem(guiPos[i], defaultHead);
                continue;
            }
            String uuid = rankedPlayers.get(i).getKey();
            Integer count = rankedPlayers.get(i).getValue();

            OfflinePlayer tempPlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));

            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwner(tempPlayer.getName());
            skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',guiColor[i]) + tempPlayer.getName());
            ArrayList<String> skullLore = new ArrayList<>();
            skullLore.add("§8【§7Samlinger: §f" + count + "§8】");
            skullMeta.setLore(skullLore);
            skull.setItemMeta(skullMeta);

            inventory.setItem(guiPos[i], skull);
        }

    }
}
