package eu.tribusmc.tribuskitpvp.base;

import com.cryptomorin.xseries.messages.Titles;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.effects.DeathEffects;
import eu.tribusmc.tribuskitpvp.base.kit.ability.AbilityImpl;
import eu.tribusmc.tribuskitpvp.base.kit.kits.*;
import eu.tribusmc.tribuskitpvp.base.kit.Kits;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayers;
import eu.tribusmc.tribuskitpvp.gui.PlayerGUI;
import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import eu.tribusmc.tribuskitpvp.placeholders.PlaceholderImpl;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public class BaseImpl implements Listener {

    private AbilityImpl abilityImpl;

    private TMCPlayers tmcPlayers;

    private List<Player> recentlyLaunched = new ArrayList<>();

    public BaseImpl() {


        DeathEffects.load();
        Kits.load();

        loadEventListeners(Core.i, this);

        abilityImpl = new AbilityImpl(this);
        loadEventListeners(Core.i, abilityImpl);
        tmcPlayers = new TMCPlayers(Core.i);

        tmcPlayers.prepareDataConfig();
        tmcPlayers.loadPlayers();


        tmcPlayers.getAll().forEach(t -> System.out.println(t.getCoins()));
        System.out.println("");
        System.out.println("");
        Collections.sort(tmcPlayers.getAll(), Comparator
                .comparing(tmcPlayer -> tmcPlayer.getCoins()));
        tmcPlayers.getAll().forEach(t -> System.out.println(t.getCoins()));

        loadPlaceholders();


    }


    public void loadPlaceholders() {
        Core.i.getPlaceholderCollector().addPlaceholder("tribuskitpvp", new PlaceholderImpl() {
            @Override
            public String getPlaceholder() {
                return "kills";
            }

            @Override
            public String getResult(Player p) {
                return String.valueOf(tmcPlayers.fetchByUUID(p.getUniqueId()).getKills());
            }
        });

        Core.i.getPlaceholderCollector().addPlaceholder("tribuskitpvp", new PlaceholderImpl() {
            @Override
            public String getPlaceholder() {
                return "deaths";
            }

            @Override
            public String getResult(Player p) {
                return String.valueOf(tmcPlayers.fetchByUUID(p.getUniqueId()).getDeaths());
            }
        });

        Core.i.getPlaceholderCollector().addPlaceholder("tribuskitpvp", new PlaceholderImpl() {
            @Override
            public String getPlaceholder() {
                return "coins";
            }

            @Override
            public String getResult(Player p) {
                return String.valueOf(tmcPlayers.fetchByUUID(p.getUniqueId()).getCoins());
            }
        });
    }


    public TMCPlayers getTMCPlayers() {
        return tmcPlayers;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (!tmcPlayers.contains(e.getPlayer().getUniqueId())) {
            TMCPlayer tmcPlayer = new TMCPlayer(e.getPlayer().getUniqueId(), e.getPlayer().getName(), 0, 0, 0);
            tmcPlayer.setDeathEffect(DeathEffects.fetchMatching("BURN"));
            tmcPlayers.add(tmcPlayer);
        }


        Titles.clearTitle(e.getPlayer());
        teleportToLobby(e.getPlayer());

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getDrops().clear();

        Player p = e.getEntity();
        Player k = e.getEntity().getKiller();
        if (k == null) {
            kill(p);
            return;
        }

        abilityImpl.clearCooldown(p);


        TMCPlayer tmcPlayer = tmcPlayers.fetchByUUID(p.getUniqueId());
        if (tmcPlayer == null) return;

        tmcPlayer.addDeaths(1);


        TMCPlayer tmcKiller = tmcPlayers.fetchByUUID(k.getUniqueId());
        if (tmcKiller == null) return;

        tmcKiller.addKills(1);

        tmcPlayer.getDeathEffect().invoke(this, p, k, false);

        //p.getWorld().strikeLightningEffect(p.getLocation());

        //new BurnDeathEffect().execute(p);


        //Fetcha userns settings

        //Kolla userns settings

        //implementera settings..

        //Sätta ett random death meddelande.
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        abilityImpl.clearCooldown(e.getPlayer());

        TMCPlayer tmcPlayer = tmcPlayers.fetchByUUID(e.getPlayer().getUniqueId());

        tmcPlayers.save(tmcPlayer);
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
        e.setCancelled(e.toWeatherState());
    }

    @EventHandler
    public void onRegionLeave(RegionLeaveEvent e) {

        if (e.getRegion().getId().equals("spawn")) {

            TMCPlayer player = tmcPlayers.fetchByUUID(e.getPlayer().getUniqueId());

            if (player.getCurrentKit() != null) return;

            if (player.getLatestKit() == null) {
                player.setLatestKit(Kits.fetchMatching("Warrior"));
                player.setCurrentKit(Kits.fetchMatching("Warrior"));
                return;
            }

            player.setCurrentKit(player.getLatestKit());
            player.getCurrentKit().equip(e.getPlayer());
            e.getPlayer().sendMessage("§6§lTribusMC §8» §7Du valde kitet §e" + player.getCurrentKit().getName() + "§7.");

        }

    }


    public void kill(Player p, Player k) {
        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 3, 20);

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

        tmcPlayers.fetchByUUID(p.getUniqueId()).setCurrentKit(null);
        int low = 10;
        int high = 30;
        int coinAmount = new Random().nextInt(high - low) + low;

        tmcPlayers.fetchByUUID(k.getUniqueId()).addCoins(coinAmount);
        Titles.sendTitle(k, 5, 30, 5, "§e§l+" + coinAmount + "§r§e⛃", "");

        k.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

        k.sendMessage("§6§lTribusMC §8» §7Du dödade §e" + p.getName());
        p.sendMessage("§6§lTribusMC §8» §7Du blev dödad av §e" + p.getKiller().getName());
    }


    public void kill(Player p) {
        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 3, 20);


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


        p.sendMessage("§6§lTribusMC §8» §7Du dog.");
    }

    public void teleportToLobby(Player player) {

        player.getInventory().clear();
        player.updateInventory();
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0);
        player.setFoodLevel(20);

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        Core.i.getGuiManager().fetchByName("PlayerGUI").loadItemsForInventory(player);
        Location lobbyLOC = (Location) Core.i.getConfig().get("lobby");

        player.teleport(lobbyLOC);

    }

    public void loadEventListeners(JavaPlugin plugin, Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}
