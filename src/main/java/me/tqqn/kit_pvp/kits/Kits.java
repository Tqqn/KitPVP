package me.tqqn.kit_pvp.kits;

import me.tqqn.kit_pvp.utils.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Kits {

    private String name;
    private String display;
    private Material material;


    public Kits (String name, String display, Material material) {
        this.name = name;
        this.display = display;
        this.material = material;
    }

    //gets the kit name
    public String getName() { return name; }

    //gets the itemstack as stated in the config
    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Color.translate(display));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
