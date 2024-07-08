package me.bertilfrigaard.samling.listeners;

import me.bertilfrigaard.samling.menusystem.DisplayMenu;
import me.bertilfrigaard.samling.menusystem.Menu;
import me.bertilfrigaard.samling.menusystem.VaultMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;


public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent e){

        if(e.getClickedInventory() == null){
            return;
        }

        InventoryHolder holder = e.getView().getTopInventory().getHolder();

        if(holder instanceof Menu){
            e.setCancelled(true);
            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }

        if(holder instanceof DisplayMenu){
            e.setCancelled(true);
            DisplayMenu menu = (DisplayMenu) holder;
            menu.handleMenu(e);
        }

        if(holder instanceof VaultMenu){
            VaultMenu menu = (VaultMenu) holder;
            menu.handleMenu(e);
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e){
        InventoryHolder holder = e.getView().getTopInventory().getHolder();

        if(holder instanceof VaultMenu){
            VaultMenu menu = (VaultMenu) holder;
            menu.handleMenuClose(e);
        }
    }
}
