package me.bertilfrigaard.samling.menusystem.menus;

import me.bertilfrigaard.samling.files.SamlingFiles;
import me.bertilfrigaard.samling.menusystem.DisplayMenu;
import me.bertilfrigaard.samling.menusystem.PlayerMenuUtility;
import me.bertilfrigaard.samling.utils.AdminUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AdminDisplaySamlingerMenu extends DisplayMenu {
    public AdminDisplaySamlingerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        int side = page + 1;
        return "§3§lADMIN SAMLINGER";
    }

    @Override
    public int setSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getSlot() == 47){
            if (page == 0) return;
            page--;
            open();
        }
        else if (e.getSlot() == 51){
            int maxindex = SamlingFiles.get().getKeys(false).size();
            if (maxindex == 0) return;
            if (Math.ceil((double) maxindex /45) > page + 1){
                page++;
                open();
            }
        }
    }

    @Override
    public void setMenuItems() {
        addNavigation();

        ArrayList<String> samlingerList = new ArrayList<>();

        for (String key : SamlingFiles.get().getKeys(false)){
            if (SamlingFiles.get().isConfigurationSection(key)){
                samlingerList.add(key);
            }
        }
        if (samlingerList.isEmpty()){
            return;
        }
        index = page*45;
        for (int i = 0; i < 45; i++) {
            String curKey = samlingerList.get(index);
            //Do shit

            ArrayList<ItemStack> items = AdminUtils.decodeItems(SamlingFiles.get().getString(curKey + ".items"));
            if (items == null) return;

            ItemStack samlingIcon = items.get(0);

            String samlingName = SamlingFiles.get().getString(curKey + ".name");

            ItemStack icon;
            ItemMeta iconMeta;
            if (samlingIcon.getType() == Material.SKULL_ITEM){
                icon = samlingIcon;
                iconMeta = icon.getItemMeta();
            } else {
                icon = new ItemStack(samlingIcon.getType());
                iconMeta = icon.getItemMeta();
            }
            iconMeta.setDisplayName(samlingName);
            if (Bukkit.getServer().getPluginManager().getPlugin("Samling").getConfig().getBoolean("samling-glowing")) {
                iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Spillere der har denne samling");

            int countClaimed = SamlingFiles.get().getList(curKey + ".claimed-by").size();

            lore.add(ChatColor.translateAlternateColorCodes('&',"&8【&7Antal: &f" + countClaimed + "&8】"));
            lore.add(" ");
            lore.add("§7Samlingens unikke Samling-ID");
            lore.add(ChatColor.translateAlternateColorCodes('&',"&8【&7ID: &f" + curKey + "&8】"));
            iconMeta.setLore(lore);
            icon.setItemMeta(iconMeta);

            inventory.setItem(i, icon);

            //Default
            index++;
            if (index >= samlingerList.size()){
                break;
            }
        }
    }
}
