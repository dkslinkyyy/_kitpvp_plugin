package eu.tribusmc.tribuskitpvp.base;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.ability.IAbility;
import eu.tribusmc.tribuskitpvp.base.effects.BurnDeathEffect;
import eu.tribusmc.tribuskitpvp.base.kit.kits.*;
import eu.tribusmc.tribuskitpvp.base.kit.KitManager;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.gui.KitGUI;
import eu.tribusmc.tribuskitpvp.gui.PlayerGUI;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
import java.util.List;


public class BaseImpl implements Listener {

    private KitManager kitManager;
    private List<TMCPlayer> tmcPlayers;
    public KitGUI kitGUI;
    private List<Player> cooldown;

    private final CooldownParser cooldownParser = new CooldownParser();
    private final String cooldownTickMessage = "§e %internal% Cooldown §8» §6%time%s";
    private final String cooldownWhenFinished = "§aDu kan nu använda din ability igen";


    public BaseImpl() {
        kitManager = new KitManager();
        loadKits(kitManager);

        kitGUI = new KitGUI(this, kitManager.kits);

        tmcPlayers = new ArrayList<>();
        cooldown = new ArrayList<>();

        cooldownParser.setTickMessage(cooldownTickMessage);
        cooldownParser.setFinishMessage(cooldownWhenFinished);

    }

    @NotNull
    public TMCPlayer fetchTMCPlayer(String playerName) {
        return tmcPlayers.stream().filter(tmcPlayer -> tmcPlayer.getPlayerName().equals(playerName)).findFirst().orElse(null);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        TMCPlayer tmcPlayer = new TMCPlayer(e.getPlayer().getUniqueId(), e.getPlayer().getName(), 0, 0);
        tmcPlayer.setDeathEffect(new BurnDeathEffect());
        tmcPlayers.add(tmcPlayer);

        Titles.clearTitle(e.getPlayer());
        teleportToLobby(e.getPlayer());

    }


    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        e.setFoodLevel(20);
    }


    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true);


        //Loopa igenom alla kits
        //Fetcha det kitet som Spelaren har
        //hämta Ability instancen from kitet och utför det


    }


    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        TMCPlayer tmcPlayer = fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;

        tmcPlayer.getCurrentKit().getAbility().onPlaceBlock(e);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getItem() == null) return;
        if (e.getItem().getItemMeta() == null) return;
        Player p = e.getPlayer();

        TMCPlayer tmcPlayer = fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;
        if (tmcPlayer.getCurrentKit() == null) return;

        if(tmcPlayer.getCurrentKit().getAbility() == null) return;

      //  if (!tmcPlayer.getCurrentKit().getAbility().onInteract(e)) return;





        cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(),   () -> {
            tmcPlayer.getCurrentKit().getAbility().onInteract(e);
        });

    }



    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        Player p;

        if (e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) e.getDamager();
            p = (Player) projectile.getShooter();
        } else {
            p = (Player) e.getDamager();
        }

        TMCPlayer tmcPlayer = fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;

        tmcPlayer.getCurrentKit().getAbility().onPlayerDamage(e);

    }


    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {

        if (!(e.getEntity().getShooter() instanceof Player)) return;

        Player p = (Player) e.getEntity().getShooter();

        TMCPlayer tmcPlayer = fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;

        if (cooldown.contains(p) && p.getItemInHand().getType() == tmcPlayer.getCurrentKit().getAbility().getHoldingItem().getType()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.i, () -> {
                p.setItemInHand(tmcPlayer.getCurrentKit().getAbility().getHoldingItem());
            }, 1L);
            e.setCancelled(true);
            return;
        }

        if (!tmcPlayer.getCurrentKit().getAbility().onProjectileLaunch(e)) return;

        Timer timer = new Timer(Timer.TimerType.REPEATABLE, tmcPlayer.getCurrentKit().getAbility().getCooldownTime());

        timer.execute(time -> {
            ActionBar.sendActionBar(p, "§e" + tmcPlayer.getCurrentKit().getAbility().getInternalName() + " Cooldown §8» §6" + time + "s");
        }).whenFinished(time -> {
            cooldown.remove(p);
            ActionBar.sendActionBar(p, "§aDu kan nu använda din ability igen.");
        }).run(true);

        tmcPlayer.getCurrentKit().getAbility().onProjectileLaunch(e);


        cooldown.add(p);
    }


    @EventHandler
    public void onFishingRod(PlayerFishEvent e) {

        TMCPlayer tmcPlayer = fetchTMCPlayer(e.getPlayer().getName());
        if (tmcPlayer == null) return;


        if (cooldown.contains(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }

        if (!tmcPlayer.getCurrentKit().getAbility().onFishingRoodHook(e)) return;


        Timer timer = new Timer(Timer.TimerType.REPEATABLE, tmcPlayer.getCurrentKit().getAbility().getCooldownTime());

        timer.execute(time -> {
            ActionBar.sendActionBar(e.getPlayer(), "§e" + tmcPlayer.getCurrentKit().getAbility().getInternalName() + " Cooldown §8» §6" + time + "s");
        }).whenFinished(time -> {
            cooldown.remove(e.getPlayer());
            ActionBar.sendActionBar(e.getPlayer(), "§aDu kan nu använda din ability igen.");
        }).run(true);

        tmcPlayer.getCurrentKit().getAbility().onFishingRoodHook(e);


        cooldown.add(e.getPlayer());
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Player p = e.getEntity();
        Player k = e.getEntity().getKiller();

        e.getDrops().clear();

        TMCPlayer tmcPlayer = fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;

        cooldown.remove(p);

        tmcPlayer.getDeathEffect().invoke(this, p, k, false);

        //p.getWorld().strikeLightningEffect(p.getLocation());

        //new BurnDeathEffect().execute(p);


        //Fetcha userns settings

        //Kolla userns settings

        //implementera settings..

        //Sätta ett random death meddelande.
    }


    public void kill(Player p, Player k) {
        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 3);


        timer.execute(time -> {

            if (time == 0) {
                Titles.clearTitle(p);
            }

            p.setHealth(20.0);
            p.setGameMode(GameMode.SPECTATOR);

            String s = timer.getTime() == 1 ? "sekund" : "sekunder";
            Titles.sendTitle(p, 5, 5, 5, "§c§lDU DOG!", "§7Respawnar om §e" + time + " §7" + s);


        }).whenFinished((time) -> {
            teleportToLobby(p);
        }).run(true);


        Titles.sendTitle(k, 5, 30, 5, "§e+13⛃", "");

        k.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

        k.sendMessage("§6§lTribusMC §8» §7Du dödade §e" + p.getName());
        p.sendMessage("§6§lTribusMC §8» §7Du blev dödad av §e" + p.getKiller().getName());

    }

    public void teleportToLobby(Player player) {

        player.getInventory().clear();
        player.updateInventory();
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        new PlayerGUI(this, player.getName(), player);

        Location lobbyLOC = (Location) Core.i.getConfig().get("lobby");


        player.teleport(lobbyLOC);

    }



    public void loadKits(KitManager kitManager) {
        kitManager.addKit(new Warrior());
        kitManager.addKit(new Grappler());
        kitManager.addKit(new Switcher());
        kitManager.addKit(new Builder());
        kitManager.addKit(new Thor());

    }


}
