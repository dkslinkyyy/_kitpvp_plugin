package eu.tribusmc.tribuskitpvp.miscelleanous;


import eu.tribusmc.tribuskitpvp.Core;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TextUtil {






    public void sendJSONMessage(Player p, String txt, String clickable) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                .a("{\"text\":\"Hello\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§6This is totally not here\"}}");
        PacketPlayOutChat chat = new PacketPlayOutChat(comp);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(chat);
    }



    public void sendTitleMessage(String msg, int fadeIn, int stay, int fadeOut, Player player) {
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', msg) + "\"}");

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);


        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }


    public void sendToAll(String msg) {
        Bukkit.getOnlinePlayers().forEach(o -> o.sendMessage(Core.i.trans(msg)));
    }

}
