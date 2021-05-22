package eu.tribusmc.tribuskitpvp.gui;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitGUI extends GUI{
    public KitGUI() {
        super("kitsGui", "§6§lVÄLJ KIT", 9*4);



        addItem(new GUIItem("close", XMaterial.BARRIER)
        .setTitle("§c§4STÄNG MENYN")
        .setLore(new String[]{"", "§7Klicka för att stänga menyn", ""})
        .setAction(new IAction() {
            @Override
            public void onClick(ClickType clickType, ItemStack item, Player p) {
                p.getOpenInventory().close();
            }

            @Override
            public void onInteract(Action action, ItemStack item, Player p) {

            }
        }));
    }
}
