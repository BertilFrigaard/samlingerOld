package me.bertilfrigaard.samling.menusystem.menus;

import me.bertilfrigaard.samling.files.SamlingFiles;
import me.bertilfrigaard.samling.menusystem.DisplayMenu;
import me.bertilfrigaard.samling.menusystem.PlayerMenuUtility;
import me.bertilfrigaard.samling.utils.AdminUtils;
import me.bertilfrigaard.samling.utils.SamlingUtils;
import me.bertilfrigaard.samling.utils.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplaySamlingerMenu extends DisplayMenu {
    public DisplaySamlingerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        int side = page + 1;
        return "§e§lSAMLINGER";
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
            return;
        }
        else if (e.getSlot() == 51){
            int maxindex = SamlingFiles.get().getKeys(false).size();
            if (maxindex == 0) return;
            if (Math.ceil((double) maxindex /45) > page + 1){
                page++;
                open();
            }
            return;
        }

        int slotClicked = e.getSlot();
        if (slotClicked >= 0 && slotClicked <= 44) {


            index = page * 45 + slotClicked;

            ArrayList<String> samlingerList = new ArrayList<>();

            for (String key : SamlingFiles.get().getKeys(false)) {
                if (SamlingFiles.get().isConfigurationSection(key)) {
                    samlingerList.add(key);
                }
            }
            if (samlingerList.isEmpty()) {
                return;
            }

            if (samlingerList.size() <= index) { //return if slot dosen't match samling
                return;
            }

            String key = samlingerList.get(index);

            if (e.isLeftClick()) {

                playerMenuUtility.setKeyToShow(key);

                e.getView().getPlayer().closeInventory();
                SamlingContentsMenu samlingContentsMenu = new SamlingContentsMenu(playerMenuUtility);
                samlingContentsMenu.open();
            }
            if (e.isRightClick()) {
                if (SamlingFiles.get().getList(key + ".claimed-by").contains(e.getWhoClicked().getUniqueId().toString())){
                    return;
                }
                e.getView().getPlayer().closeInventory();
                if (SamlingUtils.canPlayerClaimSamling((Player) e.getWhoClicked(), key)){
                    SamlingUtils.playerClaimSamling((Player) e.getWhoClicked(), key);
                    e.getWhoClicked().sendMessage("§aDu har nu klaret samlingen");
                    SoundUtils.playSound2((Player) e.getWhoClicked());
                } else {
                    e.getWhoClicked().sendMessage("§cDu har ikke alle items i denne samling i dit inventory");
                    SoundUtils.playSound3((Player) e.getWhoClicked());
                }
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

            Boolean completed = SamlingFiles.get().getList(curKey + ".claimed-by").contains(playerMenuUtility.getOwner().getUniqueId().toString());
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
            if (completed){
                lore.add("§a§lKLARET");
            }
            lore.add(" ");
            lore.add("§7Spillere der har denne samling");

            int countClaimed = SamlingFiles.get().getList(curKey + ".claimed-by").size();

            lore.add(ChatColor.translateAlternateColorCodes('&',"&8【&7Antal: &f" + countClaimed + "&8】"));
            lore.add(" ");
            lore.add("§c§oVenstreklik for at se indehold");
            if (!completed){
                lore.add("§a§oHøjreklik for at klare");
            }
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
