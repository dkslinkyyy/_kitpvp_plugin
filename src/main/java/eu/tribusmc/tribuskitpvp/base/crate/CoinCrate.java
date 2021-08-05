package eu.tribusmc.tribuskitpvp.base.crate;

import com.cryptomorin.xseries.messages.Titles;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.google.common.base.Strings;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import sun.font.TextLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoinCrate implements Listener {


    private Timer onNext, onCurrent, cloudTimer;
    private List<Location> possibleSpawnLocations;
    private Location spawnLocation;
    private Location centeredBlockLocation;
    private Hologram claimingHologram, waitingHologram;
    private boolean openChest, sendFireworks, finished = false;
    private Player holder;
    private int captureProgress = 0;
    private int coinAmount;
    private BaseImpl base;


    public CoinCrate(BaseImpl base) {
        this.base = base;
        if (Core.i.getConfig().getList("cratepoints") != null) {
            possibleSpawnLocations = Core.cast(Core.i.getConfig().getList("cratepoints"));
        }

        Core.i.getServer().getPluginManager().registerEvents(this, Core.i);

    }

    public void startOnNext() {
        onNext = new Timer(Timer.TimerType.INFINITE, 0, 20);

        onNext.execute(time -> {
            if(finished) {
                spawn();

            }
        }).run(true);
    }

    public void setCurrentHolder(Player p) {
        this.holder = p;
    }

    public void freshWaitingHologram() {

        Location hologramLocation = new Location(spawnLocation.getWorld(), centeredBlockLocation.getX(), centeredBlockLocation.getY() + 1.1, centeredBlockLocation.getZ());
        waitingHologram = HologramsAPI.createHologram(Core.i, hologramLocation);

        waitingHologram.appendTextLine("§a§lMYNT-LÅDA");
        waitingHologram.appendTextLine("§7(Högerklicka)");


    }

    public void freshClaimingHologram() {

        Location hologramLocation = new Location(spawnLocation.getWorld(), centeredBlockLocation.getX(), centeredBlockLocation.getY() + 1.52, centeredBlockLocation.getZ());
        claimingHologram = HologramsAPI.createHologram(Core.i, hologramLocation);

        claimingHologram.appendTextLine("§6§l" + holder.getName() + " claimar...");
        claimingHologram.appendTextLine(getProgressBar());
        claimingHologram.insertItemLine(2, new ItemStack(Material.DOUBLE_PLANT));

    }

    public void despawnClaimingHologram() {
        if (claimingHologram != null) {
            claimingHologram.delete();
            claimingHologram = null;
        }
    }

    public void despawnWaitingHologram() {
        if (waitingHologram != null) {
            waitingHologram.delete();
            waitingHologram = null;
        }
    }

    public void spawn() {
        finished = false;
        sendFireworks = true;

        this.spawnLocation = possibleSpawnLocations.get(new Random().nextInt(possibleSpawnLocations.size()));
        spawnLocation.getBlock().setType(Material.CHEST);

        onCurrent = new Timer(Timer.TimerType.REPEATABLE, 120, 20);
        cloudTimer = new Timer(Timer.TimerType.REPEATABLE, 120, 20);

        int low = 178;
        int high = 284;
        coinAmount = new Random().nextInt(high - low) + low;

        Location fireworkLocation = new Location(spawnLocation.getWorld(), spawnLocation.getBlockX() + 0.5, spawnLocation.getBlockY() + 1, spawnLocation.getBlockZ() + 0.5);
        centeredBlockLocation = new Location(spawnLocation.getWorld(), spawnLocation.getBlockX() + 0.5, spawnLocation.getBlockY() + 0.5, spawnLocation.getBlockZ() + 0.5);
        changeChestState(spawnLocation, openChest);

        freshWaitingHologram();

        onCurrent.execute(time -> {

            changeChestState(spawnLocation, openChest);

            if (time % 5 == 0) {
                sendFirework(fireworkLocation, sendFireworks);
            }

            if (getCurrentHolder() != null) {

                Bukkit.getOnlinePlayers().forEach(oP -> {
                    oP.playSound(centeredBlockLocation, Sound.CLICK, 1.5f, 1f);
                });

                captureProgress++;

                TextLine progressBarLine = (TextLine) claimingHologram.getLine(1);
                progressBarLine.setText(getProgressBar());

                if (captureProgress == 10) {

                    Titles.sendTitle(getCurrentHolder(), 5, 30, 5, "§e§l+" + coinAmount + "§r§e⛃", "");

                    Bukkit.getOnlinePlayers().forEach(oP -> {
                        oP.sendMessage("§6§lTribusMC §8» §e" + getCurrentHolder().getName() + " §7lyckades claima mynt-lådan.");
                    });


                    base.getTMCPlayers().fetchByUUID(getCurrentHolder().getUniqueId()).addCoins(coinAmount);
                    getCurrentHolder().playSound(getCurrentHolder().getLocation(), Sound.ORB_PICKUP, 1.5f, 1);

                    despawn();
                    finished = true;

                    return;

                }

            }

        }).whenFinished(time -> {


            if (spawnLocation.getWorld().getBlockAt(spawnLocation).getType() != Material.AIR) {

                finished = true;

                despawn();

                setCurrentHolder(null);
                spawnLocation = null;
                Bukkit.getOnlinePlayers().forEach(o -> o.sendMessage(despawnMessage));

            }

        }).run(true);

        cloudTimer.execute(time -> {
            Particle.send(centeredBlockLocation, EnumParticle.VILLAGER_HAPPY, new Float[]{0.6f, 0.6f, 0.6f}, 0.2f, 10);
        }).run(true);

        Bukkit.getOnlinePlayers().forEach(o -> {
            o.sendMessage(spawnMessage);
            o.playSound(o.getLocation(), Sound.NOTE_PLING, 2, 1);
        });

    }


    @EventHandler
    public void onClaim(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (finished) return;

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || !e.getClickedBlock().equals(spawnLocation.getBlock())) {
            return;
        }

        e.setCancelled(true);

        if (getCurrentHolder() != null && getCurrentHolder() != p) {
            p.sendMessage("§6§lTribusMC §c" + getCurrentHolder().getName() + " håller redan på att claima mynt-lådan. Döda spelaren för att återställa lådan.");
            return;
        }

        if (getCurrentHolder() == p) {
            return;
        }

        setCurrentHolder(p);
        openChest = true;
        p.playSound(spawnLocation, Sound.CHEST_OPEN, 2, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1));
        sendFireworks = false;

        despawnWaitingHologram();
        freshClaimingHologram();

    }


    @EventHandler
    public void unClaim(PlayerDeathEvent e) {

        if (finished) return;

        Player p = e.getEntity();

        if (getCurrentHolder() == p) {
            reset();
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        if (finished) return;

        if (getCurrentHolder() == e.getPlayer()) {
            reset();
        }

    }

    public void reset() {

        setCurrentHolder(null);

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.playSound(spawnLocation, Sound.CHEST_CLOSE, 2, 1);
        });

        openChest = false;
        sendFireworks = true;
        captureProgress = 0;

        despawnClaimingHologram();
        freshWaitingHologram();

    }

    public void despawn() {

        spawnLocation.getBlock().setType(Material.AIR);
        Particle.send(centeredBlockLocation, EnumParticle.CLOUD, new Float[]{0.6f, 0.6f, 0.6f}, 0.2f, 10);

        Bukkit.getOnlinePlayers().forEach(oP -> {
            oP.playSound(centeredBlockLocation, Sound.FIZZ, 1.5f, 1);
        });

        onCurrent.stop(true);
        cloudTimer.stop(true);

        despawnClaimingHologram();
        despawnWaitingHologram();

    }

    public void sendFirework(Location fireworkLocation, boolean send) {
        if (send) {
            Firework fw = (Firework) spawnLocation.getWorld().spawnEntity(fireworkLocation, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            fwm.setPower(1);
            fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.LIME).flicker(true).build());

            fw.setFireworkMeta(fwm);

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

    public String getProgressBar() {
        float percent = (float) captureProgress / 10;
        int progressBars = (int) (30 * percent);

        return Strings.repeat("" + "§a" + "⎟", progressBars)
                + Strings.repeat("" + "§7" + "⎟", 30 - progressBars);
    }

    public Player getCurrentHolder() {
        return holder;
    }

    String[] spawnMessage = {
            "",
            "§a§lEN MYNT-LÅDA HAR LANDAT!",
            "§eEn Mynt-Låda har landat någonstans i arenan!",
            "§7Leta efter fyrverkerierna för att hitta den.",
            "",
    };

    String[] despawnMessage = {
            "",
            "§6§lTribusMC §8» §cMynt-lådan despawnade eftersom ingen hann ta den i tid.",
            "",
    };

}
