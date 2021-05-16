package eu.tribusmc.tribuskitpvp.base;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class BaseImpl implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        //Implementera bas settings för spelaren(Full health, clear:a inventoriet osv..)

        //Ladda in usern och dess settings..

        //Ge spelaren bas inventariet, Kits, Shop, Settings etc..

    }



    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        //Fetcha userns settings

        //Kolla userns settings

        //implementera settings..

        //Sätta ett random death meddelande.
    }






}
