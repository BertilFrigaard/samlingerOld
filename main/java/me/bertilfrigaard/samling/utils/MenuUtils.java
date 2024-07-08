package me.bertilfrigaard.samling.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MenuUtils {
    public static void fillSlotsArray(Inventory inventory, ItemStack itemStack, int[] slots){
        for (int slot : slots){
            inventory.setItem(slot,itemStack);
        }
    }
}
