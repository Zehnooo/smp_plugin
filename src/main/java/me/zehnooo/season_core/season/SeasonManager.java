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

    public SeasonManager(SeasonSettings settings) {
        this.netherUnlockDay = settings.netherUnlockDay();
        this.endUnlockDay = settings.endUnlockDay();
        this.seasonEndDay = settings.seasonEndDay();
        this.seasonStart = settings.start();
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
