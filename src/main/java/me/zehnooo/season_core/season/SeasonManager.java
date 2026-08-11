package me.zehnooo.season_core.season;

import java.time.Instant;
import java.time.Duration;

public final class SeasonManager {
    private Boolean netherLocked = true;
    private Boolean endLocked = true;

    private final Instant seasonStart = Instant.parse("2026-08-11T15:00:00Z");

    public boolean isNetherLocked() {
        return netherLocked;
    }

    public boolean isEndLocked() {
        return endLocked;
    }

    public long getSeasonDay(){
        return Duration.between(seasonStart, Instant.now()).toDays() + 1;
    }

}
