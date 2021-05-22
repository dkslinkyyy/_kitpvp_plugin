package eu.tribusmc.tribuskitpvp.gui;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PlayerGUI extends GUI{




    public PlayerGUI(BaseImpl base, String paramInternalName, Player paramPlayer) {
        super(paramInternalName, paramPlayer);


        addItem(new GUIItem("kits", XMaterial.CHEST)
        .setTitle("§6§lKits")
        .setLore(new String[]{"", "§7Klicka för att öppna kit menyn.", ""})
        .setAction(new IAction() {
            @Override
            public void onClick(ClickType clickType, ItemStack item, Player p) {

            }

            @Override
            public void onInteract(Action action, ItemStack item, Player p) {
                new KitGUI().open(p);
                /*
                p.getInventory().clear();

                XPotion speed = XPotion.SPEED;

                p.addPotionEffect(speed.parsePotion(10000, 1));
                ItemStack splash = XMaterial.POTION.parseItem();
                assert splash != null;
                splash.setDurability((short) 16421);

                for (int i = 0; i < p.getInventory().getSize(); i++) {
                    p.getInventory().addItem(splash);
                }

                p.getInventory().setItem(0, XMaterial.DIAMOND_SWORD.parseItem());
                p.getInventory().setHelmet(XMaterial.IRON_HELMET.parseItem());
                p.getInventory().setChestplate(XMaterial.IRON_CHESTPLATE.parseItem());
                p.getInventory().setLeggings(XMaterial.IRON_LEGGINGS.parseItem());
                p.getInventory().setBoots(XMaterial.IRON_BOOTS.parseItem());

                 */
            }
        }), 4);

    }





}
