package me.bertilfrigaard.samling.menusystem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerMenuUtility {

    //Default
    private Player owner;

    //Ny samling
    private ArrayList<ItemStack> newSamlingItems;
    private String newSamlingName;

    //Show samling
    private String keyToShow;

    //Constructor
    public PlayerMenuUtility(Player owner){
        this.owner = owner;
    }

    //Getters and setters
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ArrayList<ItemStack> getNewSamlingItems() {
        return newSamlingItems;
    }

    public void setNewSamlingItems(ArrayList<ItemStack> newSamlingItems) {
        this.newSamlingItems = newSamlingItems;
    }

    public String getNewSamlingName() {
        return newSamlingName;
    }

    public void setNewSamlingName(String newSamlingName) {
        this.newSamlingName = newSamlingName;
    }

    public String getKeyToShow() {
        return keyToShow;
    }

    public void setKeyToShow(String keyToShow) {
        this.keyToShow = keyToShow;
    }
}
