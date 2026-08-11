package me.zehnooo.season_core.season;

import java.time.Instant;
import java.time.Duration;

public final class SeasonManager {

    private static final long NETHER_UNLOCK_DAY = 8;
    private static final long END_UNLOCK_DAY = 22;
    private static final long SEASON_END_DAY = 60;

    private final Instant seasonStart = Instant.parse("2026-08-11T15:00:00Z");

    public boolean isNetherLocked() {
        return getSeasonDay() < NETHER_UNLOCK_DAY;
    }

    public boolean isEndLocked() {
        return getSeasonDay() < END_UNLOCK_DAY;
    }

    public long getSeasonDay(){
        return Duration.between(seasonStart, Instant.now()).toDays() + 1;
    }

    public long daysUntilNetherUnlock(){
        return Math.max(0, NETHER_UNLOCK_DAY - getSeasonDay());
    }

    public long daysUntilEndUnlock(){
        return Math.max(0,END_UNLOCK_DAY - getSeasonDay());
    }

    public String getSeasonPhase(){
        if (isNetherLocked()) return "Overworld Age";
        if (isEndLocked()) return "The Underworld";
        return "The End...";
    }

    public boolean isSeasonOver(){
        return getSeasonDay() >= SEASON_END_DAY;
    }

    public long daysUntilSeasonEnd(){
        return Math.max(0, SEASON_END_DAY - getSeasonDay());
    }

}
