package eu.tribusmc.tribuskitpvp.gui;


import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface IAction {

    void onClick(ClickType clickType, ItemStack item, Player p);

    void onInteract(Action action, ItemStack item, Player p);

}
