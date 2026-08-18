package me.zehnooo.season_core.event;

import me.zehnooo.season_core.relic.RelicType;
import org.bukkit.event.Listener;

public interface SeasonEvent extends Listener {

    String id();

    String displayName();

    RelicType prize();

    void start(EventContext ctx);

    void stop(EventContext ctx, boolean awardPrize);
}
