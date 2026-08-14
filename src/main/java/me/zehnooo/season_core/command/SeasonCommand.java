package me.zehnooo.season_core.command;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.relic.RelicType;
import me.zehnooo.season_core.season.SeasonManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class SeasonCommand implements CommandExecutor {

    private final SeasonManager seasonManager;
    private final RelicManager relicManager;

    public SeasonCommand(SeasonManager seasonManager, RelicManager relicManager) {
        this.seasonManager = seasonManager;
        this.relicManager = relicManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("relic")) {
            return handleRelic(sender, args);
        }

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

    private boolean handleRelic(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }

        if (args.length == 1) {
            player.sendMessage("Usage: /season relic <check|give>");
            return true;
        }

        if (args[1].equalsIgnoreCase("check")) {
            ItemStack hand = player.getInventory().getItemInMainHand();
            player.sendMessage("Relic: " + relicManager.isRelic(hand));
            player.sendMessage("Type: " + relicManager.getType(hand));
            if (relicManager.isRelic(hand)) {
                player.sendMessage("Cooldown: " + relicManager.remainingSeconds(hand) + "s");
            }
            return true;
        }

        if (args[1].equalsIgnoreCase("give")) {
            for (RelicType type : RelicType.values()) {
                player.getInventory().addItem(relicManager.create(type));
            }
            player.sendMessage("Gave all relics.");
            return true;
        }

        player.sendMessage("Usage: /season relic <check|give>");
        return true;
    }
}
