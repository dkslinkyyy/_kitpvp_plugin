package eu.tribusmc.tribuskitpvp.miscelleanous;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class Skull {


    public static ItemStack retrieve(UUID uuid) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        sm.setOwner(Bukkit.getPlayer(uuid).getName());
        item.setItemMeta(sm);
        return item;
    }
}
