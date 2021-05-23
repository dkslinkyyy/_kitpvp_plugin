package eu.tribusmc.tribuskitpvp.gui;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.XItemStack;
import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.Core;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUIItem {


    private final String internalName;
    private XMaterial material;
    private int amount = 1;
    private String[] lore;
    private IAction action;
    private String title;
    private boolean hideAttributes = false;
    private boolean glow = false;


    public GUIItem(String paramInternalName, XMaterial paramMaterial) {
        this.internalName = paramInternalName;
        this.material = paramMaterial;
    }


    @NotNull
    public GUIItem setAction(IAction action) {
        this.action = action;
        return this;
    }


    @NotNull
    public IAction getAction() {
        return action;
    }

    @NotNull
    public GUIItem setTitle(String title) {
        this.title = title;
        return this;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @NotNull
    public GUIItem setMaterial(XMaterial material) {
        this.material = material;
        return this;
    }

    @NotNull
    public GUIItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    @NotNull
    public GUIItem setLore(String[] lore) {
        this.lore = lore;
        return this;
    }

    @NotNull
    public GUIItem hideAttributes() {
        this.hideAttributes = true;
        return this;
    }

    @NotNull
    public GUIItem glow() {
        this.glow = true;
        return this;
    }

    public ItemStack getOutcome() {
        if(lore == null && title == null) return material.parseItem();

        assert material.parseItem() != null;
        ItemMeta meta = material.parseItem().getItemMeta();


        if(title != null) meta.setDisplayName(title);

        if(lore !=null) meta.setLore(Arrays.asList(lore));

        ItemStack item = material.parseItem();

        if(amount > 1) item.setAmount(amount);

        if (glow) {
            meta.addEnchant(Enchantment.OXYGEN, 1, true);
        }

        if (hideAttributes) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        }

        item.setItemMeta(meta);
        return item;
    }







}
