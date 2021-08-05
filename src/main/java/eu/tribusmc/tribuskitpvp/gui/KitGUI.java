package eu.tribusmc.tribuskitpvp.gui;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import eu.tribusmc.tribuskitpvp.base.kit.Kits;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import org.bukkit.entity.Player;


public class KitGUI extends GUI {


    public KitGUI(BaseImpl base, Player basePlayer) {

        super("kitsGui", "§8» §6§lVÄLJ ETT KIT", 9 * 4);






        fill();

        basePlayer.sendMessage("test!");

        kits.forEach(kit -> {

            addItem(new GUIItem(kit.getName(), kitItem(kit, hasPermission))
                    .setTitle(kitName(kit, hasPermission))
                    .setLore(kitLore(kit, hasPermission))
                    .hideAttributes()
                    .setAction((a, ct, item, p) -> {
                        if (ct != null) {
                            if (!hasPermission) {
                                return;
                            }

                            player.getOpenInventory().close();
                            TMCPlayer tmcPlayer = base.getTMCPlayers().fetchByUUID(basePlayer.getUniqueId());

                            if(tmcPlayer == null) return;

                            tmcPlayer.setCurrentKit(kit);
                            tmcPlayer.setLatestKit(kit);

                            kit.equip(player);
                            player.sendMessage("§6§lTribusMC §8» §7Du valde kitet §e" + kit.getName() + "§7.");

                        }
                    }), kit.getSlot());
        });

    }



}
