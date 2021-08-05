package eu.tribusmc.tribuskitpvp.base.crate;

import com.cryptomorin.xseries.messages.Titles;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.base.economy.Purchasable;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import eu.tribusmc.tribuskitpvp.base.kit.Kits;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class KitCrate implements Listener {


    private BaseImpl base;
    private boolean isBeingOpened = false;
    private Player opener;
    private Location location;
    private Kit winningKit;
    private Hologram idleHologram;
    private Hologram rollingHologram;
    private boolean openChest = false;
    private Purchasable purchasable;
    private Random random;

    public KitCrate(BaseImpl base, Location paramLocation) {

        this.base = base;
        this.location = paramLocation;

        purchasable = new Purchasable(6500);

        Core.i.getServer().getPluginManager().registerEvents(this, Core.i);

        random = new Random();

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || !e.getClickedBlock().equals(location.getBlock())) {
            return;
        }

        e.setCancelled(true);
        openChest = true;
        roll(e.getPlayer());

    }


    public void create() {
        createIdleHologram();
    }

    private int position;

    public void roll(Player p) {

        if (isBeingOpened) return;

        if (!purchasable.whenBought(base.getTMCPlayers().fetchByUUID(p.getUniqueId()), new Purchasable.IPurchase() {
            @Override
            public void buying(int price, int moneyInPocket, int difference, TMCPlayer buyer) {
                Titles.sendTitle(buyer.getPlayer(), 5, 30, 5, "§c§l-6500§r§c⛃", "");
                buyer.getPlayer().sendMessage("§6§lTribusMC §8» §7Du öppnar en Kit-Låda...");
                buyer.takeCoins(price);
            }

            @Override
            public String getCanAffordMessage(TMCPlayer tmcPlayer) {
                return "§6§lTribusMC §8» §cDu har inte råd.";
            }

            @Override
            public String getHasBoughtMessage(TMCPlayer tmcPlayer) {
                return "§6§lTribusMC §8» §cDu har redan alla kits.";
            }

            @Override
            public boolean hasBought(TMCPlayer tmcPlayer) {
                return tmcPlayer.getPlayer().hasPermission("tribusmc.cratesboughtall");
            }
        })) {

            p.setVelocity(p.getLocation().getDirection().multiply(-0.7));
            return;

        }

        isBeingOpened = true;


        if (Kits.getLockedKits(p).size() == 1) {

            winningKit = Kits.getLockedKits(p).get(0);

            rollLastKit(p);

            return;
        }

        position = new Random().nextInt(Kits.getLockedKits(p).size() - 1);

        int rollTime = 9;

        Timer rollTimer = new Timer(Timer.TimerType.REPEATABLE, rollTime, 20);

        winningKit = Kits.getLockedKits(p).get(position);

        despawnIdleHologram();
        createRollingHologram(winningKit);

        rollTimer.execute(time -> {

            changeChestState(location, openChest);

            if (time > 2) {

                System.out.println(position);

                if (position < Kits.getLockedKits(p).size() - 1) {
                    position++;
                    System.out.println(position);
                } else {
                    position = 0;
                    System.out.println(position);
                }

                winningKit = Kits.getLockedKits(p).get(position);

                updateRollingHologram(winningKit);

                Bukkit.getOnlinePlayers().forEach(oP -> {
                    oP.playSound(location, Sound.CLICK, 2, 1);
                });

            }

            if (time == 2) {

                Location lightningStrikeLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 0.7, location.getBlockZ() + 0.5);

                location.getWorld().strikeLightningEffect(lightningStrikeLocation);

                Bukkit.getOnlinePlayers().forEach(oP -> {
                    oP.playSound(location, Sound.LEVEL_UP, 2, 1);
                });


            }

        }).whenFinished(time -> {

            String permissionCommand = "pex user " + p.getName() + " add " + winningKit.getPermission();
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), permissionCommand);
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex reload");
            p.sendMessage("§6§lTribusMC §8» §7Du har nu tillgång till kitet §e" + winningKit.getName() + "§7.");

            openChest = false;
            isBeingOpened = false;
            changeChestState(location, false);

            despawnRollingHologram();
            createIdleHologram();

        }).run(true);
    }

    public void rollLastKit(Player p) {

        despawnIdleHologram();

        int rollTime = 7;
        Timer timer = new Timer(Timer.TimerType.REPEATABLE, rollTime, 20);

        timer.execute(time -> {

            changeChestState(location, openChest);

            if (time > 6) return;

            if (time > 3) {

                Location lightningStrikeLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 0.7, location.getBlockZ() + 0.5);
                location.getWorld().strikeLightningEffect(lightningStrikeLocation);

                Particle.send(lightningStrikeLocation, EnumParticle.SMOKE_LARGE, new Float[]{0.3f, 0.3f, 0.3f}, 0.0f, 15);
                Particle.send(lightningStrikeLocation, EnumParticle.SMOKE_NORMAL, new Float[]{0.3f, 0.3f, 0.3f}, 0.0f, 10);

                Bukkit.getOnlinePlayers().forEach(oP -> {
                    oP.playSound(location, Sound.NOTE_PLING, 2, 1);
                });

                return;

            }


            if (time == 3) {

                Location particleLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 0.7, location.getBlockZ() + 0.5);
                Particle.send(particleLocation, EnumParticle.LAVA, new Float[]{0.35f, 0.35f, 0.35f}, 0.3f, 22);

                Bukkit.getOnlinePlayers().forEach(oP -> {
                    oP.playSound(location, Sound.NOTE_PLING, 2, 2);
                });
                Location fireworkLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 0.7, location.getBlockZ() + 0.5);
                sendFirework(fireworkLocation, true, Color.PURPLE, FireworkEffect.Type.BALL_LARGE);
                sendFirework(fireworkLocation, true, Color.FUCHSIA, FireworkEffect.Type.BURST);
                sendFirework(fireworkLocation, true, Color.AQUA, FireworkEffect.Type.STAR);
                createRollingHologram(winningKit);
                Bukkit.getOnlinePlayers().forEach(oP -> {
                    oP.playSound(location, Sound.LEVEL_UP, 2, 1);
                });

                return;

            }

        }).whenFinished(time -> {

            openChest = false;
            isBeingOpened = false;
            changeChestState(location, false);

            despawnRollingHologram();
            createIdleHologram();

            Bukkit.getOnlinePlayers().forEach(oP -> {
                oP.sendMessage("§6§lTribusMC §8» §a§l" + p.getName() + " har nu låst upp alla kits. Grattis!");
            });

        }).run(true);

    }

    private void createIdleHologram() {

        Location hologramLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 1.6, location.getBlockZ() + 0.5);
        idleHologram = HologramsAPI.createHologram(Core.i, hologramLocation);

        idleHologram.appendTextLine("§3§l*§f§l*§3§l*§r §b§lKIT-LÅDA§r §3§l*§f§l*§3§l*§r");
        idleHologram.appendTextLine("§7Öppna för §6⛃6500");

    }

    public void despawnIdleHologram() {
        if (idleHologram != null) {
            idleHologram.delete();
            idleHologram = null;
        }
    }

    private void createRollingHologram(Kit kit) {

        Location hologramLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 1.52, location.getBlockZ() + 0.5);
        rollingHologram = HologramsAPI.createHologram(Core.i, hologramLocation);

        rollingHologram.appendTextLine("§b§l" + kit.getName());
        rollingHologram.appendItemLine(kit.getHoldingItem().parseItem());
    }

    private void updateRollingHologram(Kit kit) {

        Location particleLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 1.26, location.getBlockZ() + 0.5);


        TextLine textLine = (TextLine) rollingHologram.getLine(0);
        textLine.setText("§b§l" + kit.getName());

        ItemLine itemLine = (ItemLine) rollingHologram.getLine(1);
        itemLine.setItemStack(kit.getHoldingItem().parseItem());

        Particle.send(particleLocation, EnumParticle.SPELL_WITCH, new Float[]{0.3f, 0.3f, 0.3f}, 0, 8);

    }

    public void despawnRollingHologram() {
        if (rollingHologram != null) {
            rollingHologram.delete();
            rollingHologram = null;
        }
    }

    @SuppressWarnings("deprecation")
    public void changeChestState(Location loc, boolean open) {
        byte dataByte = (open) ? (byte) 1 : 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            BlockPosition position = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            PacketPlayOutBlockAction blockActionPacket = new PacketPlayOutBlockAction(position, net.minecraft.server.v1_8_R3.Block.getById(loc.getBlock().getTypeId()), (byte) 1, dataByte);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(blockActionPacket);
        }
    }

    public void sendFirework(Location fireworkLocation, boolean send, Color color, FireworkEffect.Type type) {

        if (send) {

            Firework fw = (Firework) fireworkLocation.getWorld().spawnEntity(fireworkLocation, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            fwm.setPower(1);
            fwm.addEffect(FireworkEffect.builder().with(type).withColor(color).flicker(true).build());

            fw.setFireworkMeta(fwm);

            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.i, () -> {
                fw.detonate();
            }, 1L);

        }

    }

}
