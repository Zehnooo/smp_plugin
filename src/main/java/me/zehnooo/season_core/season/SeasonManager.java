package me.zehnooo.season_core.season;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class SeasonManager {

    private final long netherUnlockDay;
    private final long endUnlockDay;
    private final long seasonEndDay;
    private final Instant seasonStart;
    private final java.time.ZoneId timezone;

    public SeasonManager(SeasonSettings settings) {
        this.netherUnlockDay = settings.netherUnlockDay();
        this.endUnlockDay = settings.endUnlockDay();
        this.seasonEndDay = settings.seasonEndDay();
        this.seasonStart = settings.start();
        this.timezone = settings.timezone();
    }

    public boolean isNetherLocked() {
        return getSeasonDay() < netherUnlockDay;
    }

    public boolean isEndLocked() {
        return getSeasonDay() < endUnlockDay;
    }

    public long getSeasonDay(){
        LocalDate start = seasonStart.atZone(timezone).toLocalDate();
        LocalDate today = Instant.now().atZone(timezone).toLocalDate();
        return ChronoUnit.DAYS.between(start, today) + 1;
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
