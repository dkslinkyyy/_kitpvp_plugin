package eu.tribusmc.tribuskitpvp.gui;


import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface IAction {



    void onAction(Action a, ClickType ct, ItemStack item, Player p);

}
