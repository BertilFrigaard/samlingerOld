package me.bertilfrigaard.samling.menusystem;

import me.bertilfrigaard.samling.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public abstract class DisplayMenu implements InventoryHolder {

    protected int page = 0;

    protected int index = 0;

    protected Inventory inventory;

    protected PlayerMenuUtility playerMenuUtility;

    public DisplayMenu(PlayerMenuUtility playerMenuUtility){
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();

    public abstract int setSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open(){

        inventory = Bukkit.createInventory(this, setSlots(), getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);

    }

    public void addNavigation(){
        ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        ItemMeta orangeGlassPaneMeta = orangeGlassPane.getItemMeta();
        orangeGlassPaneMeta.setDisplayName(" ");
        orangeGlassPane.setItemMeta(orangeGlassPaneMeta);

        MenuUtils.fillSlotsArray(inventory,orangeGlassPane, new int[]{45, 46, 48, 49, 50, 52, 53});

        ItemStack leftButton = new ItemStack(Material.STONE_BUTTON);
        ItemMeta leftButtonMeta = leftButton.getItemMeta();
        leftButtonMeta.setDisplayName("§3§lForrige side");
        List<String> leftLore = new ArrayList<>();
        leftLore.add("§7Tryk her for at gå");
        leftLore.add("§7til forrige side");
        leftButtonMeta.setLore(leftLore);
        leftButton.setItemMeta(leftButtonMeta);

        ItemStack rightButton = new ItemStack(Material.STONE_BUTTON);
        ItemMeta rightButtonMeta = rightButton.getItemMeta();
        rightButtonMeta.setDisplayName("§3§lNæste side");
        List<String> rightLore = new ArrayList<>();
        rightLore.add("§7Tryk her for at gå");
        rightLore.add("§7til næste side");
        rightButtonMeta.setLore(rightLore);
        rightButton.setItemMeta(rightButtonMeta);

        inventory.setItem(47, leftButton);
        inventory.setItem(51, rightButton);


    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
