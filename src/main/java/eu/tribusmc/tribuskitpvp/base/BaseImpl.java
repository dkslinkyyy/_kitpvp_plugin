package eu.tribusmc.tribuskitpvp.base;

import com.cryptomorin.xseries.messages.Titles;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.gui.GUI;
import eu.tribusmc.tribuskitpvp.gui.PlayerGUI;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import eu.tribusmc.tribuskitpvp.scoreboard.boards.LobbyScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        e.getDrops().clear();

        e.getEntity().setFoodLevel(20);
        e.getEntity().setHealth(20.0);
        e.getEntity().setGameMode(GameMode.SPECTATOR);

        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 5);


        timer.execute(time -> {
            String s = timer.getTime() == 1 ? "sekund" : "sekunder";
            Titles.sendTitle(e.getEntity(), 5, 10000,5, "§c§lDU DOG!", "§7Respawnar om §e" + time + " §7" + s);

        }).whenFinished((time) -> {
            Titles.clearTitle(e.getEntity());
            teleportToLobby(e.getEntity());
        }).run(true);

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
