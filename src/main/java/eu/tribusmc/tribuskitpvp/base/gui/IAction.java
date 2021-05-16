package eu.tribusmc.tribuskitpvp.base.gui;


import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface IAction {

    void onClick(ClickType clickType, ItemStack item);

    void onInteract(Action action, ItemStack item);

}
