package me.zehnooo.season_core.relic;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class RelicCommand implements CommandExecutor {

    private final RelicService relics;
    private final RelicManager items;
    private final RelicRegistry registry;

    public RelicCommand(RelicService relics, RelicManager items, RelicRegistry registry) {
        this.relics = relics;
        this.items = items;
        this.registry = registry;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /relic <give|check|destroy>");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "give" -> {
                return give(sender, args);
            }
            case "check" -> {
                return check(sender);
            }
            case "destroy" -> {
                return destroy(sender, args);
            }
            default -> sender.sendMessage("Usage: /relic <give|check|destroy>");
        }
        return true;
    }

    private boolean give(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /relic give <type> [player]");
            return true;
        }
        RelicType type = parseType(args[1]);
        if (type == null) {
            sender.sendMessage("Unknown relic type: " + args[1]);
            return true;
        }
        Player target = args.length >= 3
                ? Bukkit.getPlayerExact(args[2])
                : (sender instanceof Player p ? p : null);
        if (target == null) {
            sender.sendMessage("Specify a valid online player.");
            return true;
        }
        if (relics.mint(type, target)) {
            sender.sendMessage("Gave " + type.displayName() + " to " + target.getName() + ".");
        } else {
            sender.sendMessage(type.displayName() + " already exists. Destroy it first.");
        }
        return true;
    }

    private boolean check(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }
        ItemStack hand = player.getInventory().getItemInMainHand();
        player.sendMessage("Relic: " + items.isRelic(hand));
        player.sendMessage("Type: " + items.getType(hand));
        return true;
    }

    private boolean destroy(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /relic destroy <type>");
            return true;
        }
        RelicType type = parseType(args[1]);
        if (type == null) {
            sender.sendMessage("Unknown relic type: " + args[1]);
            return true;
        }
        registry.clear(type);
        sender.sendMessage("Cleared registry entries for " + type.displayName() + ". It can be won again.");
        return true;
    }

    private RelicType parseType(String raw) {
        try {
            return RelicType.valueOf(raw.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
