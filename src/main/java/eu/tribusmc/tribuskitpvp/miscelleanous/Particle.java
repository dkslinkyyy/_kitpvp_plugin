package eu.tribusmc.tribuskitpvp.miscelleanous;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Particle {

    public static void send(Player p, EnumParticle particle, Float[] offsets, float speed, int amount, boolean sendToAll) {

        Location pLoc = p.getLocation();

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) pLoc.getX(), (float) pLoc.getY(), (float) pLoc.getZ(), offsets[0], offsets[1], offsets[2], speed, amount);

        if (sendToAll) {
            online : Bukkit.getOnlinePlayers().forEach(online -> {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            });
        } else {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }

    }

    public static void send(Player p, Location loc, EnumParticle particle, Float[] offsets, float speed, int amount, boolean sendToAll) {

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), offsets[0], offsets[1], offsets[2], speed, amount);

        if (sendToAll) {
            online : Bukkit.getOnlinePlayers().forEach(online -> {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            });
        } else {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }

    }

}
