package me.zehnooo.season_core.event;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class EventStore {

    private final JavaPlugin plugin;
    private final File file;

    public EventStore(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "events.yml");
    }

    // TODO: persist active event id + scores across restarts.

    public File file() {
        return file;
    }
}
