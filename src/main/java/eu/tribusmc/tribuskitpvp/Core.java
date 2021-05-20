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


        SQLFactory sqlFactory = new SQLFactory(mySQL);

        try {
            boolean b = sqlFactory.tableExists(mySQL.getConnection(), "TMCPlayer");
            System.out.println(b);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sqlFactory.readDataFromTable("TMCPlayer").print();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
