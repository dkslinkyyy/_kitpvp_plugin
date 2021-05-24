package eu.tribusmc.tribuskitpvp.base;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.messages.Titles;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.effects.BurnDeathEffect;
import eu.tribusmc.tribuskitpvp.base.kit.kits.*;
import eu.tribusmc.tribuskitpvp.base.kit.KitManager;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.gui.KitGUI;
import eu.tribusmc.tribuskitpvp.gui.PlayerGUI;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public class BaseImpl implements Listener {

    private KitManager kitManager;
    private List<TMCPlayer> tmcPlayers;
    public KitGUI kitGUI;

    public BaseImpl(PlayerGUI playerGUI, KitGUI kitGUI, ) {
        kitManager = new KitManager();
        loadKits(kitManager);

        kitGUI = new KitGUI(this, kitManager.kits);

        tmcPlayers = new ArrayList<>();

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
    public void onDeath(PlayerDeathEvent e) {

        Player p = e.getEntity();
        Player k = e.getEntity().getKiller();

        e.getDrops().clear();

        TMCPlayer tmcPlayer = fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;


        tmcPlayer.getDeathEffect().invoke(this, p, k, false);

        //p.getWorld().strikeLightningEffect(p.getLocation());

        //new BurnDeathEffect().execute(p);


        //Fetcha userns settings

        //Kolla userns settings

        //implementera settings..

        //Sätta ett random death meddelande.
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

    }


    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {

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

    public void loadEventListeners(JavaPlugin plugin, Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void loadKits(KitManager kitManager) {

        kitManager.addKit(new Warrior());
        kitManager.addKit(new Grappler());
        kitManager.addKit(new Switcher());
        kitManager.addKit(new Builder());
        kitManager.addKit(new Thor());

    }


}
