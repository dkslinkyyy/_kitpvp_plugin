package eu.tribusmc.tribuskitpvp;

import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    public static Core i;



    @Override
    public void onEnable() {
        i = this;
        // Plugin startup logic


        /*
        Timer loadLogo = new Timer(Timer.TimerType.DELAY, 1);

        loadLogo.execute(time -> {
            loadLogo();


            StringBuilder str = new StringBuilder();

            getDescription().getAuthors().forEach(author -> str.append(author).append(" "));

            getServer().getConsoleSender().sendMessage(translate("&9&lUtvecklare &7" + str));
        }).run(true);



        new Timer(Timer.TimerType.DELAY, 2).execute(time -> {
            getServer().getConsoleSender().sendMessage(translate(""));
            getServer().getConsoleSender().sendMessage(translate("&6TribusKitPvP -> &aEnabled"));
        }).run(true);

    }


         */




    /*

    private void loadLogo() {
        int next = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("G:\\Dev\\Minecraft\\TribusKitPvP\\src\\main\\resources\\logo.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(next == colorCodes().length) {
                    next = 0;
                }
                getServer().getConsoleSender().sendMessage(translate(colorCodes()[next] + line));
                next++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }


    private String[] colorCodes() {
        return new String[]{
                "&4",
                "&c",
                "&6",
                "&e",
                "&3",
                "&b",
                "&a",
                "&2"
        };
    }

     */

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
