package me.bertilfrigaard.samling.menusystem;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class VaultMenu implements InventoryHolder {

    protected Inventory inventory;

    protected PlayerMenuUtility playerMenuUtility;

    public VaultMenu(PlayerMenuUtility playerMenuUtility){
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();

    public abstract int setSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void handleMenuClose(InventoryCloseEvent e);

    public abstract void setMenuItems();

    public void open(){

        inventory = Bukkit.createInventory(this, setSlots(), getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);

    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
