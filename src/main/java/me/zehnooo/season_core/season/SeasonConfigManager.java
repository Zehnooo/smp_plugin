package me.zehnooo.season_core.season;

import me.zehnooo.season_core.SeasonCorePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.DateTimeException;

public class SeasonConfigManager {
    private final SeasonCorePlugin plugin;

    public SeasonConfigManager(SeasonCorePlugin plugin) {
        this.plugin = plugin;
    }

    public SeasonSettings load() {
        FileConfiguration config = plugin.getConfig();

        long netherUnlockDay = config.getLong("season.nether-unlock-day");
        long endUnlockDay = config.getLong("season.end-unlock-day");
        long seasonEndDay = config.getLong("season.end-day");
        String startString = config.getString("season.start");
        String timezoneString = config.getString("season.timezone");


        if (startString == null || startString.isBlank()){
            throw new IllegalArgumentException("season.start cannot be empty");
        }

        if (timezoneString == null || timezoneString.isBlank()){
            throw new IllegalArgumentException("season.timezone cannot be empty");
        }

        if (netherUnlockDay <= 0) {
            throw new IllegalArgumentException("season.nether-unlock-day must be greater than 0");
        }
        if (endUnlockDay <= netherUnlockDay) {
            throw new IllegalArgumentException("season.end-unlock-day must be greater than season.nether-unlock-day");
        }
        if (seasonEndDay <= endUnlockDay) {
            throw new IllegalArgumentException("season.end-day must be greater than season.end-unlock-day");
        }

        Instant start;
        try {
            start = Instant.parse(startString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("season.start invalid: " + startString + " format ex: '2022-01-01T00:00:00Z'");
        }

        ZoneId timezone;
        try {
            timezone = ZoneId.of(timezoneString);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("season.timezone invalid: " + timezoneString + " format ex: 'America/Chicago'");
        }


        return new SeasonSettings(timezone, start, netherUnlockDay, endUnlockDay, seasonEndDay);
    }
}
