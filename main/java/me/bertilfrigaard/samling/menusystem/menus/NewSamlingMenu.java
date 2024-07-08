package me.bertilfrigaard.samling.menusystem.menus;

import me.bertilfrigaard.samling.menusystem.Menu;
import me.bertilfrigaard.samling.menusystem.PlayerMenuUtility;
import me.bertilfrigaard.samling.menusystem.VaultMenu;
import me.bertilfrigaard.samling.utils.AdminUtils;
import me.bertilfrigaard.samling.utils.MenuUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewSamlingMenu extends VaultMenu {
    public NewSamlingMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return playerMenuUtility.getNewSamlingName();
    }

    @Override
    public int setSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getSlot() >= 45) {
            e.setCancelled(true);
        }
    }

    @Override
    public void handleMenuClose(InventoryCloseEvent e) {
        ArrayList<ItemStack> newSamlingItems = new ArrayList<>();

        for (int i = 0; i < 45; i++) {
            ItemStack item = e.getView().getTopInventory().getItem(i);
            if (item != null) {
                newSamlingItems.add(item);
            }
        }

        AdminUtils.saveSamling(newSamlingItems, playerMenuUtility.getNewSamlingName(), (Player) e.getPlayer());
    }

    @Override
    public void setMenuItems() {
        ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        ItemMeta orangeGlassPaneMeta = orangeGlassPane.getItemMeta();
        orangeGlassPaneMeta.setDisplayName(" ");
        orangeGlassPane.setItemMeta(orangeGlassPaneMeta);

        MenuUtils.fillSlotsArray(inventory, orangeGlassPane, new int[]{45,46,47,48,50,51,52,53});

        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        bookMeta.setDisplayName("§e§lNY SAMLING");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7Du er ved at oprette en ny");
        lore.add("§7samling med navnet:");
        lore.add("§f" + playerMenuUtility.getNewSamlingName());
        lore.add("");
        lore.add("§7Samlingens første item vil");
        lore.add("§7også blive samlingens ikon");
        lore.add("");
        lore.add("§7Samlingen vil blive oprettet");
        lore.add("§7med de valgte items når du");
        lore.add("§7lukker dit inventory");
        bookMeta.setLore(lore);
        book.setItemMeta(bookMeta);

        inventory.setItem(49,book);
    }
}
