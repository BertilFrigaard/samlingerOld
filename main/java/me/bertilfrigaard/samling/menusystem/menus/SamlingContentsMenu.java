package me.bertilfrigaard.samling.menusystem.menus;

import me.bertilfrigaard.samling.files.SamlingFiles;
import me.bertilfrigaard.samling.menusystem.Menu;
import me.bertilfrigaard.samling.menusystem.PlayerMenuUtility;
import me.bertilfrigaard.samling.utils.AdminUtils;
import me.bertilfrigaard.samling.utils.MenuUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SamlingContentsMenu extends Menu {
    public SamlingContentsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return SamlingFiles.get().getString(playerMenuUtility.getKeyToShow() + ".name");
    }

    @Override
    public int setSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getSlot() == 49) {
            e.getWhoClicked().closeInventory();
            DisplaySamlingerMenu menu = new DisplaySamlingerMenu(playerMenuUtility);
            menu.open();
        }
    }

    @Override
    public void setMenuItems() {
        //Default
        ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        ItemMeta orangeGlassPaneMeta = orangeGlassPane.getItemMeta();
        orangeGlassPaneMeta.setDisplayName(" ");
        orangeGlassPane.setItemMeta(orangeGlassPaneMeta);

        MenuUtils.fillSlotsArray(inventory,orangeGlassPane, new int[]{45, 46, 47, 48, 50, 51, 52, 53});

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName("§c§lTILBAGE");
        List<String> lore = new ArrayList<>();
        lore.add("§7Tryk her for at gå tilbage");
        lore.add("§7til den forrige side");
        barrierMeta.setLore(lore);
        barrier.setItemMeta(barrierMeta);

        inventory.setItem(49,barrier);

        //Contents
        ArrayList<ItemStack> samlingContents = AdminUtils.decodeItems(SamlingFiles.get().getString(playerMenuUtility.getKeyToShow() + ".items"));
        for (int i = 0; i < samlingContents.size(); i++) {
            inventory.setItem(i, samlingContents.get(i));
        }

    }
}
