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

    public PlayerGUI(BaseImpl base) {
        super("PlayerGUI", "", 9*4);
        System.out.println("test");


        //KitGUI instance med spelarens craftbukkit id
        //Kolla så att en instance redan existerar för just den spelaren,
        //Returnera spelarens instance of KitGUI:n och öppna den.



        addItem(new GUIItem("kits", XMaterial.NETHER_STAR)
        .setTitle("§6§lKits")
        .setLore(new String[]{"", "§7Klicka för att öppna kit menyn.", ""})
        .setAction((a, ct, item, p) -> {
            if(a !=null) {
                new KitGUI(base, p).open(p);
              //  KitGUI kitGui = GUIManager.fetch("KitsGUI", p).open();
           //     if(kitGui == null){
                    // Spelaren har inte en instance of KitsGUI
              //      GUIManager.create(GUIManager.fetchGlobal("KitsGUI"), p);
                    return;
                }

             //????
             //Check här bla bla


         //   }

        }), 0);

        addItem(new GUIItem("settings", XMaterial.COMPARATOR)
                .setTitle("§6§lAlternativ")
                .setLore(new String[]{"", "§7Klicka för att konfigurera olika alternativ.", ""})
                .setAction((a, ct, item, p) -> {
                    if(a !=null) {
                        //
                    }
                }), 8);

        addItem(new GUIItem("shop", XMaterial.CHEST)
                .setTitle("§6§lShop")
                .setLore(new String[]{"", "§7Köp olika föremål för dina mynt.", ""})
                .setAction((a, ct, item, p) -> {
                    if(a !=null) {
                        //
                    }
                }), 7);

    }

}
