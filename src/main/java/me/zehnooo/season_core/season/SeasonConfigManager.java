package me.zehnooo.season_core.season;

import me.zehnooo.season_core.Season_core;
import org.bukkit.configuration.file.FileConfiguration;
import java.time.Instant;

public class SeasonConfigManager {
    private final Season_core plugin;

    public SeasonConfigManager(Season_core plugin) {
        this.plugin = plugin;
    }

    public SeasonSettings load() {
        FileConfiguration config = plugin.getConfig();

        long netherUnlockDay = config.getLong("season.nether-unlock-day");
        long endUnlockDay = config.getLong("season.end-unlock-day");
        long seasonEndDay = config.getLong("season.end-day");
        String startString = config.getString("season.start");
        Instant start;

        if (startString == null || startString.isBlank()){
            start = Instant.now();
            config.set("season.start", start.toString());
            plugin.saveConfig();
        } else {
            start = Instant.parse(startString);
        }

        return new SeasonSettings(start, netherUnlockDay, endUnlockDay, seasonEndDay);
    }
}
