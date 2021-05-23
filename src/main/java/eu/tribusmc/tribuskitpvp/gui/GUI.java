package eu.tribusmc.tribuskitpvp.gui;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public abstract class GUI implements Listener {

    private Inventory inventory;
    private final Player player;
    private String internalName;
    private String title;
    private final int size;


    private final Set<GUIItem> guiItems;


    /**
     * Base Constructor, Creates a new GUI instance
     *
     * @param paramPlayer       To invoke the players inventory
     * @param paramInternalName Internal name for the GUI instance
     * @param paramTitle        Title for the GUI
     */
    private GUI(Player paramPlayer, String paramInternalName, String paramTitle, int paramSize) {
        this.player = paramPlayer;
        this.internalName = paramInternalName;
        this.title = paramTitle;
        this.size = paramSize;

        guiItems = new HashSet<>();

        create();

        Core.i.getServer().getPluginManager().registerEvents(this, Core.i);
    }

    /**
     * Creates a new GUI instance based on Title & Size
     */
    public GUI(String paramInternalName, String paramTitle, int paramSize) {
        this(null, paramInternalName, paramTitle, paramSize);
    }

    /**
     * Creates a new GUI instance based on the player, uses players inventory if not null.
     */
    public GUI(String paramInternalName, Player paramPlayer) {
        this(paramPlayer, paramInternalName, null, 0);
    }

    @NotNull
    public GUI setTile(String paramTitle) {
        this.title = paramTitle;
        return this;
    }

    @NotNull
    public GUI setInternalName(String paramInternalName) {
        this.internalName = paramInternalName;
        return this;
    }


    @NotNull
    public void addItem(GUIItem guiItem) {
        guiItems.add(guiItem);
        inventory.addItem(guiItem.getOutcome());
    }

    @NotNull
    public void addItem(GUIItem guiItem, int slot) {
        guiItems.add(guiItem);
        inventory.setItem(slot, guiItem.getOutcome());
    }


    @NotNull
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @NotNull
    public Inventory getInventory() {
        return inventory;
    }




    @NotNull
    private GUI create() {
        if (player == null) inventory = Bukkit.createInventory(null, size, title);
        else inventory = player.getInventory();
        return this;
    }

    @NotNull
    public GUI open(Player player) {
        if (inventory == null) create();
        player.openInventory(inventory);
        return this;
    }

    @NotNull
    public GUI fill() {
        for (int i = 0; i < inventory.getSize(); i++) {

            GUIItem item = new GUIItem("fill", XMaterial.GRAY_STAINED_GLASS_PANE).setTitle(" ");

            inventory.setItem(i, item.getOutcome());
        }
        return this;
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null || e.getInventory() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        GUIItem item = guiItems.stream().filter(gItem -> gItem.getTitle().equals(e.getCurrentItem().getItemMeta().getDisplayName())).findFirst().orElse(null);


        if(item == null) return;
        if (item.getAction() == null) return;
        item.getAction().onAction(null, e.getClick(), e.getCurrentItem(), (Player) e.getWhoClicked());
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getItem() == null) return;
        if (e.getItem().getItemMeta() == null) return;

        GUIItem item = guiItems.stream().filter(gItem -> gItem.getTitle().equals(e.getItem().getItemMeta().getDisplayName())).findFirst().orElse(null);

        if(item == null) return;
        if (item.getAction() == null) return;
        item.getAction().onAction(e.getAction(), null, e.getItem(), e.getPlayer());
        e.setCancelled(true);


    }

}
