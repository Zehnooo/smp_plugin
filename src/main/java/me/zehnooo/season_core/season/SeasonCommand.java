package me.zehnooo.season_core.season;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.relic.RelicType;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class SeasonCommand implements CommandExecutor {

    private final SeasonManager seasonManager;


    public SeasonCommand(SeasonManager seasonManager) {
        this.seasonManager = seasonManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("KirkCraft Season 1");
            sender.sendMessage("Season Day: " + seasonManager.getSeasonDay());
            sender.sendMessage("Current Season Phase: " + seasonManager.getSeasonPhase());
            sender.sendMessage("Days until Nether Unlock: " + seasonManager.daysUntilNetherUnlock());
            sender.sendMessage("Days until End Unlock: " + seasonManager.daysUntilEndUnlock());
            sender.sendMessage("Days until Season End: " + seasonManager.daysUntilSeasonEnd());
            return true;
        }

        return true;
    }


}
