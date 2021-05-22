package eu.tribusmc.tribuskitpvp.base;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.Titles;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.kits.Warrior;
import eu.tribusmc.tribuskitpvp.gui.GUI;
import eu.tribusmc.tribuskitpvp.gui.PlayerGUI;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import eu.tribusmc.tribuskitpvp.scoreboard.boards.LobbyScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;


public class BaseImpl implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Titles.clearTitle(e.getPlayer());
        teleportToLobby(e.getPlayer());

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
        if(!(e.getEntity() instanceof Player)) return;

        if(e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true);


        //Loopa igenom alla kits
        //Fetcha det kitet som Spelaren har
        //hämta Ability instancen from kitet och utför det


        Warrior warrior = new Warrior();

        warrior.getAbility().onPlayerDamage();
    }


    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
        e.getWorld().setStorm(false);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        e.getDrops().clear();

        e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
        e.getEntity().setFoodLevel(20);
        e.getEntity().setHealth(20.0);
        e.getEntity().setGameMode(GameMode.SPECTATOR);

        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 3);


        timer.execute(time -> {
            if(time == 0) {
                Titles.clearTitle(e.getEntity());
            }
            String s = timer.getTime() == 1 ? "sekund" : "sekunder";
            Titles.sendTitle(e.getEntity(), 5, 5,5, "§c§lDU DOG!", "§7Respawnar om §e" + time + " §7" + s);

        }).whenFinished((time) -> {
            teleportToLobby(e.getEntity());
        }).run(true);


        Titles.sendTitle(e.getEntity().getKiller(), 5,30,5, "§e+13⛃", "");

        e.getEntity().getKiller().playSound(e.getEntity().getLocation(), Sound.ORB_PICKUP,1,1);

        e.getEntity().getKiller().sendMessage("§6§lTribusMC §8» §7Du dödade §e" + e.getEntity().getName());
        e.getEntity().sendMessage("§6§lTribusMC §8» §7Du blev dödad av §e" + e.getEntity().getKiller().getName());

        //Fetcha userns settings

        //Kolla userns settings

        //implementera settings..

        //Sätta ett random death meddelande.
    }


    private void teleportToLobby(Player player) {
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        new PlayerGUI(this, player.getName(), player);

        Location lobbyLOC = (Location) Core.i.getConfig().get("lobby");

        System.out.println(lobbyLOC.getX());

        player.teleport(lobbyLOC);

    }






}
