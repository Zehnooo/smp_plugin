package me.zehnooo.season_core.command;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.season.SeasonManager;
import me.zehnooo.season_core.relic.RelicType;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class SeasonCommand implements CommandExecutor {

    private final SeasonManager seasonManager;
    private final RelicManager relicManager;

    public SeasonCommand(SeasonManager seasonManager, RelicManager relicManager) {
        this.seasonManager = seasonManager;
        this.relicManager = relicManager;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 1 && args[0].equalsIgnoreCase("relic")){
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to use this command!");
                return true;
            }
            player.getInventory().addItem(relicManager.create(RelicType.EMBER_BLADE));
            player.sendMessage("Gave Ember Blade");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("relic") && args[1].equalsIgnoreCase("check")){
            if (!(sender instanceof Player player)){
                sender.sendMessage("You must be a player to use this command!");
                return true;
            }
            ItemStack hand = player.getInventory().getItemInMainHand();
            player.sendMessage("Relic: " + relicManager.isRelic(hand));
            player.sendMessage("Type: " + relicManager.getType(hand));
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("relic") && args[1].equalsIgnoreCase("give")){
            if (!(sender instanceof Player player)) return;
            for (RelicType type : RelicType.values()){
                player.getInventory().addItem(relicManager.create(type));
            }
            return true;
        }


        if (args.length == 0){
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
