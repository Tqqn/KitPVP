package KitPvP.Tqqn.kits;

import KitPvP.Tqqn.utils.Color;
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
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Color.translate(display));
        is.setItemMeta(im);
        return is;
    }
}
