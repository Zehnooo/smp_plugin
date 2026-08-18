package me.zehnooo.season_core.event;

import me.zehnooo.season_core.event.impl.BountyEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.time.Duration;

public final class EventCommand implements CommandExecutor {

    private final EventManager events;

    public EventCommand(EventManager events) {
        this.events = events;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /season event <start|stop|list>");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "start" -> {
                return start(sender, args);
            }
            case "stop" -> {
                events.stop(false);
                sender.sendMessage("Event stopped.");
            }
            case "list" -> sender.sendMessage("Events: bounty");
            default -> sender.sendMessage("Usage: /season event <start|stop|list>");
        }
        return true;
    }

    private boolean start(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Usage: /season event start <id> <minutes>");
            return true;
        }
        String id = args[1].toLowerCase();
        long minutes;
        try {
            minutes = Long.parseLong(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Minutes must be a number.");
            return true;
        }

        SeasonEvent event = switch (id) {
            case "bounty" -> new BountyEvent();
            default -> null;
        };
        if (event == null) {
            sender.sendMessage("Unknown event: " + id);
            return true;
        }

        if (events.start(event, Duration.ofMinutes(minutes))) {
            sender.sendMessage("Started " + event.displayName() + " for " + minutes + " minutes.");
        } else {
            sender.sendMessage("An event is already running.");
        }
        return true;
    }
}
