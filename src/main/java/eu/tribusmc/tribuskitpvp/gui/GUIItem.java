package eu.tribusmc.tribuskitpvp.gui;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.XItemStack;
import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.Core;
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


    public ItemStack getOutcome() {
        if(lore == null && title == null) return material.parseItem();

        assert material.parseItem() != null;
        ItemMeta meta = material.parseItem().getItemMeta();


        if(title != null) meta.setDisplayName(title);

        if(lore !=null) meta.setLore(Arrays.asList(lore));

        ItemStack item = material.parseItem();

        if(amount > 1) item.setAmount(amount);

        item.setItemMeta(meta);
        return item;
    }







}
