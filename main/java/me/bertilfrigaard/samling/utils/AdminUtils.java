package me.bertilfrigaard.samling.utils;

import me.bertilfrigaard.samling.files.SamlingFiles;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

public class AdminUtils {
    public static void saveSamling(ArrayList<ItemStack> items, String name, Player p) {
        if (items.isEmpty()) {
            p.sendMessage("§c§lFEJL §7Der skete en fejl. Har du glemt at putte items ind i kisten?");
            return;
        }

        try {
            //ENCODE ----- Encode items to byte64
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);

            //Number of items?
            bukkitObjectOutputStream.writeInt(items.size());

            //Items in samling?
            for (ItemStack itemStack : items){
                bukkitObjectOutputStream.writeObject(itemStack);
            }

            bukkitObjectOutputStream.flush();

            byte[] serializedObject = byteArrayOutputStream.toByteArray();

            bukkitObjectOutputStream.close();
            byteArrayOutputStream.close();

            String encodedObject = Base64.getEncoder().encodeToString(serializedObject);

            //Set samling ID
            String samlingID = generateSamlingID();

            //Write to file
            ConfigurationSection section = SamlingFiles.get().createSection(samlingID);
            section.set("name", name);
            section.set("items", encodedObject);
            section.set("claimed-by", new ArrayList<String>());
            SamlingFiles.save();

        } catch (IOException e) {
            System.out.println(e);
        }

        p.sendMessage("§a§lSUCCES §7Samling " + name + "§7 er nu oprettet!");
    }

    public static ArrayList<ItemStack> decodeItems(String encodedObject) {

        byte[] serializedObject = Base64.getDecoder().decode(encodedObject);

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);

            int itemCount = bukkitObjectInputStream.readInt();

            ArrayList<ItemStack> decodedItems = new ArrayList<>();

            for (int i = 0; i < itemCount; i++) {
                decodedItems.add((ItemStack) bukkitObjectInputStream.readObject());
            }

            return decodedItems;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
        System.out.println("AdminUtils.java - ERROR in function decodeItems (Returned null)");
        return null;
    }

    public static String generateSamlingID() {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVXYZ1234567890";
        final SecureRandom RANDOM = new SecureRandom();

        String returnID = "";

        while (returnID.isEmpty()){
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < 6; i++) {
                builder.append(CHARS.charAt(RANDOM.nextInt(35)));
            }

            if (!SamlingFiles.get().isConfigurationSection(builder.toString())) {
                returnID = builder.toString();
            }
        }

        return returnID;
    }
}
