package KitPvP.Tqqn;

import KitPvP.Tqqn.Utils.Color;
import KitPvP.Tqqn.Utils.Config;
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

    public String getDisplay() { return display; }
    public Material getMaterial() { return material; }
    public String getName() { return name; }
    public ItemStack getItemStack() {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Color.translate(display));
        is.setItemMeta(im);
        return is;
    }
    public void save() {
        Config.saveKit(this);
    }
}
