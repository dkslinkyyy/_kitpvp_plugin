package eu.tribusmc.tribuskitpvp.commands.sub;

import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CoinCommand extends SubCommand {

    public CoinCommand() {
        super("tribuskitpvp", "coins", "tribusmc.admin", "<take|give|set> <player> <amount>");
    }

    @Override
    public void executeSub(CommandSender s, String[] args) {
        if (args.length < 3) {
            s.sendMessage("§6§lTribusMC §8» §cFel syntax! " + super.getUsage() + ".");

            return;
        }

        String[] actions = new String[]{"take", "give", "set"};

        String action = args[0];

        String a = Arrays.asList(actions).stream().filter(self -> self.equals(action)).findFirst().orElse(null);

        if(a == null) {
            s.sendMessage("§6§lTribusMC §8» §cFel syntax! " + super.getUsage() + ".");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            s.sendMessage("§6§lTribusMC §8» §cSpelaren är inte online.");
            return;
        }

        int amount = 0;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            s.sendMessage("§6§lTribusMC §8» §cDu måste skriva ett tal!");
            return;
        }


        TMCPlayer tmcTarget = Core.i.base.getTMCPlayers().fetchByUUID(target.getUniqueId());

        if(tmcTarget == null) {
            s.sendMessage("§6§lTribusMC §8» §cKunde inte hitta spelarens data!");
            return;
        }


        switch (a) {
            case "take" :
                tmcTarget.takeCoins(amount);
                s.sendMessage("§6§lTribusMC §8» §7Du tog §c-⛃" + amount + " §7mynt ifrån §e" + tmcTarget.getPlayer().getName() + "§7.");
                break;
            case "give" :
                tmcTarget.addCoins(amount);
                s.sendMessage("§6§lTribusMC §8» §7Du gav §a+⛃" + amount + "  §7mynt till §e" + tmcTarget.getPlayer().getName() + "§7.");
                break;
            case "set" :
                tmcTarget.setCoins(amount);
                s.sendMessage("§6§lTribusMC §8» §7Du satte §e" + tmcTarget.getPlayer().getName() + " §7balans till §e⛃" + amount + "§7.");
                break;
        }

    }
}
