package me.zehnooo.season_core.command;

import me.zehnooo.season_core.season.SeasonManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SeasonCommand implements CommandExecutor {

    private final SeasonManager seasonManager;

    public SeasonCommand(SeasonManager seasonManager) {
        this.seasonManager = seasonManager;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        sender.sendMessage("KirkCraft Season 1");
        sender.sendMessage("Nether Locked: " + seasonManager.isNetherLocked());
        sender.sendMessage("End Locked: " + seasonManager.isEndLocked());
        sender.sendMessage("Season Day: " + seasonManager.getSeasonDay());
        return true;
    }
}
