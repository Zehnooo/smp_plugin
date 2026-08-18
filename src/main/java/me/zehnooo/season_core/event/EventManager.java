package me.zehnooo.season_core.event;

import me.zehnooo.season_core.relic.RelicService;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;

public final class EventManager {

    private final JavaPlugin plugin;
    private final EventContext ctx;
    private SeasonEvent active;
    private BukkitTask endTask;

    public EventManager(JavaPlugin plugin, RelicService relics) {
        this.plugin = plugin;
        this.ctx = new EventContext(relics);
    }

    public boolean start(SeasonEvent event, Duration length) {
        if (active != null) return false;
        active = event;
        Bukkit.getPluginManager().registerEvents(event, plugin);
        event.start(ctx);
        endTask = Bukkit.getScheduler().runTaskLater(plugin, () -> stop(true), length.toSeconds() * 20L);
        return true;
    }

    public void stop(boolean award) {
        if (active == null) return;
        HandlerList.unregisterAll(active);
        active.stop(ctx, award);
        if (endTask != null) {
            endTask.cancel();
            endTask = null;
        }
        active = null;
    }

    public SeasonEvent active() {
        return active;
    }
}
