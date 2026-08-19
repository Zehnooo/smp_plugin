package me.zehnooo.season_core.season;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

public final class SeasonCommand implements CommandExecutor {

    private final SeasonManager seasonManager;


    public SeasonCommand(SeasonManager seasonManager) {
        this.seasonManager = seasonManager;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {

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
