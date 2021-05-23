package eu.tribusmc.tribuskitpvp.gui;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitGUI extends GUI {
    public KitGUI(BaseImpl base, List<Kit> kits) {
        super("kitsGui", "§8» §6§lVÄLJ ETT KIT", 9 * 4);

        fill();

        kits.forEach(kit -> {

            addItem(new GUIItem(kit.getKitName(), kit.getHoldingItem())
                    .setTitle("§6§l" + StringUtils.capitalize(kit.getKitName().toLowerCase()))
                    .setLore(kit.getLore())
                    .hideAttributes()
                    .setAction((a, ct, item, p) -> {
                        if (ct != null) {
                            p.getOpenInventory().close();
                            TMCPlayer tmcPlayer = base.fetchTMCPlayer(p.getName());
                            tmcPlayer.setCurrentKit(kit);

                            kit.equip(p);
                        }
                    }), kit.getSlot());
        });


        addItem(new GUIItem("close", XMaterial.SPRUCE_DOOR)
                .setTitle("§c§lStäng Menyn")
                .setLore(new String[]{"", "§7Klicka för att stänga menyn", ""})
                .setAction((a, ct, item, p) -> {
                    if (a != null) {
                        p.getOpenInventory().close();
                    }
                }), 35);
    }


    public String[] append2Array(String[] array, List<String> list) {
        List<String> tempList = new ArrayList<String>(Arrays.asList(array));
        String[] tempArray = new String[tempList.size()];
        return tempList.toArray(tempArray);
    }


}
