package eu.tribusmc.tribuskitpvp.gui;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PlayerGUI extends GUI{

    public PlayerGUI(BaseImpl base, String paramInternalName, Player paramPlayer) {

        super(paramInternalName, paramPlayer);

        addItem(new GUIItem("kits", XMaterial.NETHER_STAR)
        .setTitle("§6§lKits")
        .setLore(new String[]{"", "§7Klicka för att öppna kit menyn.", ""})
        .setAction((a, ct, item, p) -> {
            if(a !=null) {
                base.kitGUI.open(p);
            }
        }), 0);

        addItem(new GUIItem("settings", XMaterial.COMPARATOR)
                .setTitle("§6§lAlternativ")
                .setLore(new String[]{"", "§7Klicka för att konfigurera olika alternativ.", ""})
                .setAction((a, ct, item, p) -> {
                    if(a !=null) {
                        base.kitGUI.open(p);
                    }
                }), 8);

        addItem(new GUIItem("shop", XMaterial.CHEST)
                .setTitle("§6§lShop")
                .setLore(new String[]{"", "§7Köp olika föremål för dina mynt.", ""})
                .setAction((a, ct, item, p) -> {
                    if(a !=null) {
                        base.kitGUI.open(p);
                    }
                }), 7);

    }

}
