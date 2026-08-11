package me.zehnooo.season_core.season;
import java.time.Instant;

public record SeasonSettings (
    Instant start,
    long netherUnlockDay,
    long endUnlockDay,
    long seasonEndDay
) {

}
