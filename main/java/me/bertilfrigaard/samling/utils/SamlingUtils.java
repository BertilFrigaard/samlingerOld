package me.bertilfrigaard.samling.utils;

import me.bertilfrigaard.samling.files.PlayerFiles;
import me.bertilfrigaard.samling.files.SamlingFiles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.*;

public class SamlingUtils {
    public static boolean canPlayerClaimSamling(Player p, String key){
        ArrayList<ItemStack> items = AdminUtils.decodeItems(SamlingFiles.get().getString(key + ".items"));
        if (items == null){
            return false;
        }

        HashMap<String,List<Object>> trueInventory = getTrueInventory(p.getInventory().getContents());
        HashMap<String,List<Object>> trueItems = getTrueInventory(items.toArray(items.toArray(new ItemStack[0])));

        for (Map.Entry<String,List<Object>> curItem : trueItems.entrySet()){
            List<Object> obj = curItem.getValue();
            String keyName = obj.get(1).toString() + obj.get(0).toString() + obj.get(3).toString();
            if (trueInventory.containsKey(keyName)){
                if ((Integer) trueInventory.get(keyName).get(2) >= (Integer) obj.get(2)){
                    continue;
                }
            }
            return false;
        }
        return true;
    }

    public static HashMap<String,List<Object>> getTrueInventory(ItemStack[] inventory){
        HashMap<String,List<Object>> map = new HashMap<>();
        for (ItemStack item : inventory){
            if (item == null) continue;
            ItemMeta meta = item.getItemMeta();
            Material material = item.getType();
            int amount = item.getAmount();
            int durability = item.getDurability();

            String keyName = meta.toString() + material.toString() + durability;
            if (map.containsKey(keyName)){
                List<Object> curValues = map.get(keyName);
                int newAmount = ((Integer) curValues.get(2)) + amount;
                curValues.set(2,newAmount);
            } else {
                List<Object> newValues = new ArrayList<>();
                newValues.add(0,material);
                newValues.add(1,meta);
                newValues.add(2,amount);
                newValues.add(3,durability);
                map.put(keyName,newValues);
            }
        }
        return map;
    }

    public static void playerClaimSamling(Player p, String key){
        ArrayList<String> claimedBy = (ArrayList<String>) SamlingFiles.get().getList(key + ".claimed-by");
        claimedBy.add(p.getUniqueId().toString());
        SamlingFiles.get().set(key + ".claimed-by", claimedBy);
        SamlingFiles.save();
        PlayerFiles.addClaimed(p.getUniqueId().toString(), key);
    }

    public static List<Map.Entry<String,Integer>> getTop(){
        List<Map.Entry<String,Integer>> playerMap = new ArrayList<>();

        for (String key : PlayerFiles.get().getKeys(false)){
            Map.Entry<String,Integer> entry = new AbstractMap.SimpleEntry<>(key, PlayerFiles.get().getList(key + ".claimed").size());
            playerMap.add(entry);
        }

        playerMap.sort(Comparator.comparingInt(Map.Entry::getValue));

        Collections.reverse(playerMap);
        return playerMap;

    }

    public static int getCurPos(String uuid){
        List<Map.Entry<String,Integer>> topList = getTop();
        int iterator = 0;
        for (Map.Entry<String,Integer> curMap : topList) {
            iterator++;
            if (Objects.equals(curMap.getKey(), uuid)){
                return iterator;
            }
        }
        return Bukkit.getOfflinePlayers().length;
    }


}
