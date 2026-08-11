package me.zehnooo.season_core.season;

import java.time.Instant;
import java.time.Duration;

import me.zehnooo.season_core.Season_core;
import org.bukkit.configuration.file.FileConfiguration;


public final class SeasonManager {

    private final long netherUnlockDay;
    private final long endUnlockDay;
    private final long seasonEndDay;
    private final Instant seasonStart;

    public SeasonManager(Season_core plugin, FileConfiguration config) {
        this.netherUnlockDay = config.getLong("season.nether-unlock-day");
        this.endUnlockDay = config.getLong("season.end-unlock-day");
        this.seasonEndDay = config.getLong("season.season-end-day");
        String start = config.getString("season.start");

        if (start == null || start.isBlank()){
            Instant now = Instant.now();

            config.set("season.start", now.toString());
            plugin.saveConfig();

            this.seasonStart = now;
        } else {
            this.seasonStart = Instant.parse(start);
        }

    }

    public boolean isNetherLocked() {
        return getSeasonDay() < netherUnlockDay;
    }

    public boolean isEndLocked() {
        return getSeasonDay() < endUnlockDay;
    }

    public long getSeasonDay(){
        return Duration.between(seasonStart, Instant.now()).toDays() + 1;
    }

    public long daysUntilNetherUnlock(){
        return Math.max(0, netherUnlockDay - getSeasonDay());
    }

    public long daysUntilEndUnlock(){
        return Math.max(0, endUnlockDay - getSeasonDay());
    }

    public String getSeasonPhase(){
        if (isNetherLocked()) return "Overworld Age";
        if (isEndLocked()) return "The Underworld";
        return "The End...";
    }

    public boolean isSeasonOver(){
        return getSeasonDay() >= seasonEndDay;
    }

    public long daysUntilSeasonEnd(){
        return Math.max(0, seasonEndDay - getSeasonDay());
    }

}
