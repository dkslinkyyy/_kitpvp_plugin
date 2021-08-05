package eu.tribusmc.tribuskitpvp.base.kit;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import eu.tribusmc.tribuskitpvp.base.kit.ability.IAbility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Kit {

    private final String kitName;
    private final IAbility ability;

    public Kit(String paramKitName, IAbility paramAbility) {
        this.kitName = paramKitName;
        this.ability = paramAbility;
    }

    public abstract int getSlot();
    public abstract String[] getLore();
    public abstract XMaterial getHoldingItem();

    public IAbility getAbility() {
        return ability;
    }

    public void equip(Player p) {
        p.getInventory().clear();

        XPotion speed = XPotion.SPEED;

        p.addPotionEffect(speed.parsePotion(36000, 1));
        ItemStack splash = XMaterial.POTION.parseItem();
        assert splash != null;
        splash.setDurability((short) 16421);

        for (int i = 0; i < p.getInventory().getSize(); i++) {
            p.getInventory().addItem(splash);
        }

        if (getAbility() != null)
            p.getInventory().setItem(getAbility().getHoldingSlot(), getAbility().getHoldingItem());

        p.getInventory().setItem(0, XMaterial.DIAMOND_SWORD.parseItem());
        p.getInventory().setHelmet(XMaterial.DIAMOND_HELMET.parseItem());
        p.getInventory().setChestplate(XMaterial.IRON_CHESTPLATE.parseItem());
        p.getInventory().setLeggings(XMaterial.IRON_LEGGINGS.parseItem());
        p.getInventory().setBoots(XMaterial.DIAMOND_BOOTS.parseItem());
    }

    public void clear(Player p) {
        p.getInventory().clear();
        p.getInventory().setItem(0, XMaterial.AIR.parseItem());
        p.getInventory().setHelmet(XMaterial.AIR.parseItem());
        p.getInventory().setChestplate(XMaterial.AIR.parseItem());
        p.getInventory().setLeggings(XMaterial.AIR.parseItem());
        p.getInventory().setBoots(XMaterial.AIR.parseItem());

    }



    public String getName() {
        return kitName;
    }
}
