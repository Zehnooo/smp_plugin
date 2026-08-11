package me.zehnooo.season_core.season;
import java.time.Instant;
import java.time.ZoneId;

public record SeasonSettings (
        ZoneId timezone,
        Instant start,
        long netherUnlockDay,
        long endUnlockDay,
        long seasonEndDay
) {

}
