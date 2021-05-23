package eu.tribusmc.tribuskitpvp;

import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.commands.KitPvPCommand;
import eu.tribusmc.tribuskitpvp.gui.KitGUI;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    public static Core i;




    @Override
    public void onEnable() {
        i = this;


        getServer().getPluginManager().registerEvents(new BaseImpl(), this);


        new KitPvPCommand();




        /*
        if(Bukkit.getOnlinePlayers().size() > 0) {
            Bukkit.getOnlinePlayers().forEach(o -> {
                if(o.getLocation().getWorld().getName().equals("Lobby")) {
                    o.getInventory().clear();
                    new PlayerGUI(o.getDisplayName(), o);
                }

            });

        }

        /*

        MySQL mySQL = new MySQL("jdbc:mysql://localhost:3306/testing", "root", "");


        SQLFactory sqlFactory = new SQLFactory(mySQL);
        sqlFactory.readColumn("tmcplayer", "k_d_Ratio");


         */

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }




    public String trans(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
