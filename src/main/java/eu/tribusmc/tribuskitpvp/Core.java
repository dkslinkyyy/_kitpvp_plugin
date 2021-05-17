package eu.tribusmc.tribuskitpvp;

import eu.tribusmc.tribuskitpvp.db.DataColumn;
import eu.tribusmc.tribuskitpvp.db.DataType;
import eu.tribusmc.tribuskitpvp.db.SQLFactory;
import org.bukkit.plugin.java.JavaPlugin;
import pro.husk.mysql.MySQL;

import java.sql.SQLException;

public final class Core extends JavaPlugin {

    public static Core i;



    @Override
    public void onEnable() {
        i = this;


        MySQL mySQL = new MySQL("jdbc:mysql://localhost:3306/testing", "root", "");


        try {
            SQLFactory sqlFactory = new SQLFactory(mySQL);
            sqlFactory.createTable("TMCPlayer", new DataColumn[]{

                    new DataColumn("UUID", DataType.VARCHAR),
                    new DataColumn("Bukkit Name", DataType.VARCHAR)

            });



        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
