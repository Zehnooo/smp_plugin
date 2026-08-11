package me.zehnooo.season_core.season;

import java.time.Instant;
import java.time.Duration;

public final class SeasonManager {

    private static final long NETHER_UNLOCK_TIME = 8;
    private static final long END_UNLOCK_TIME = 22;
    private final Instant seasonStart = Instant.parse("2026-08-11T15:00:00Z");

    public boolean isNetherLocked() {
        return getSeasonDay() < NETHER_UNLOCK_TIME;
    }

    public boolean isEndLocked() {
        return getSeasonDay() < END_UNLOCK_TIME;
    }

    public long getSeasonDay(){
        return Duration.between(seasonStart, Instant.now()).toDays() + 1;
    }

    public long daysUntilNetherUnlock(){
        return NETHER_UNLOCK_TIME - getSeasonDay();
    }

    public long daysUntilEndUnlock(){
        return END_UNLOCK_TIME - getSeasonDay();
    }

}
