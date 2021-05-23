package eu.tribusmc.tribuskitpvp.base;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.effects.BurnDeathEffect;
import eu.tribusmc.tribuskitpvp.base.effects.FireworkDeathEffect;
import eu.tribusmc.tribuskitpvp.base.kits.Grappler;
import eu.tribusmc.tribuskitpvp.base.kits.KitManager;
import eu.tribusmc.tribuskitpvp.base.kits.Warrior;
import eu.tribusmc.tribuskitpvp.base.kits.abilities.Outcome;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.gui.GUIItem;
import eu.tribusmc.tribuskitpvp.gui.KitGUI;
import eu.tribusmc.tribuskitpvp.gui.PlayerGUI;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class BaseImpl implements Listener {

    private KitManager kitManager;
    private List<TMCPlayer> tmcPlayers;
    public KitGUI kitGUI;
    private List<Player> cooldown;


    public BaseImpl() {
        kitManager = new KitManager();
        loadKits(kitManager);

        kitGUI = new KitGUI(this, kitManager.kits);

        tmcPlayers = new ArrayList<>();
        cooldown = new ArrayList<>();
    }

    @NotNull
    public TMCPlayer fetchTMCPlayer(String playerName) {
        return tmcPlayers.stream().filter(tmcPlayer -> tmcPlayer.getPlayerName().equals(playerName)).findFirst().orElse(null);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        tmcPlayers.add(new TMCPlayer(e.getPlayer().getUniqueId(), e.getPlayer().getName(), 0,0));
        Titles.clearTitle(e.getPlayer());
        teleportToLobby(e.getPlayer());
        System.out.println("tttt");



        //Implementera bas settings för spelaren(Full health, clear:a inventoriet osv..)

        //Ladda in usern och dess settings..

        //Ge spelaren bas inventariet, Kits, Shop, Settings etc..

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
    public void onFishingRod(PlayerFishEvent e) {

        TMCPlayer tmcPlayer = fetchTMCPlayer(e.getPlayer().getName());
        if(tmcPlayer == null) return;


        if(cooldown.contains(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }

        if(tmcPlayer.getCurrentKit().getAbility().onFishingRoodHook(e) == Outcome.FAIL) return;


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

        //p.getWorld().strikeLightningEffect(p.getLocation());

        //new BurnDeathEffect().execute(p);

        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 3);


        timer.execute(time -> {

            if (time == 0) {
                Titles.clearTitle(e.getEntity());
            }

            p.setHealth(20.0);
            p.setGameMode(GameMode.SPECTATOR);

            String s = timer.getTime() == 1 ? "sekund" : "sekunder";
            Titles.sendTitle(e.getEntity(), 5, 5, 5, "§c§lDU DOG!", "§7Respawnar om §e" + time + " §7" + s);


        }).whenFinished((time) -> {
            teleportToLobby(p);
        }).run(true);


        Titles.sendTitle(k, 5, 30, 5, "§e+13⛃", "");

        k.playSound(e.getEntity().getLocation(), Sound.ORB_PICKUP, 1, 1);

        k.sendMessage("§6§lTribusMC §8» §7Du dödade §e" + e.getEntity().getName());
        p.sendMessage("§6§lTribusMC §8» §7Du blev dödad av §e" + e.getEntity().getKiller().getName());

        //Fetcha userns settings

        //Kolla userns settings

        //implementera settings..

        //Sätta ett random death meddelande.
    }


    private void teleportToLobby(Player player) {
        player.getInventory().clear();
        player.updateInventory();
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        new PlayerGUI(this, player.getName(), player);

        Location lobbyLOC = (Location) Core.i.getConfig().get("lobby");

        System.out.println(lobbyLOC.getX());

        player.teleport(lobbyLOC);

    }



    public void loadKits(KitManager kitManager) {
        kitManager.addKit(new Warrior());
        kitManager.addKit(new Grappler());


    }


}
