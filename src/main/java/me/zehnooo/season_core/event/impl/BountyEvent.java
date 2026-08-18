package me.zehnooo.season_core.event.impl;

import me.zehnooo.season_core.event.AbstractEvent;
import me.zehnooo.season_core.event.EventContext;
import me.zehnooo.season_core.relic.RelicType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class BountyEvent extends AbstractEvent {

    private static final int KILL = 3;
    private static final int DEATH = 2;

    private final Map<UUID, UUID> target = new HashMap<>();

    @Override
    public String id() {
        return "bounty";
    }

    @Override
    public String displayName() {
        return "Bounty Hunt";
    }

    @Override
    public RelicType prize() {
        return RelicType.PHASE_BOW;
    }

    @Override
    public void start(EventContext ctx) {
        scores.reset();
        target.clear();
        assignTargetsInRing(ctx.players());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer == null) return;

        UUID v = victim.getUniqueId();
        UUID k = killer.getUniqueId();

        if (v.equals(target.get(k))) {
            scores.add(k, KILL);
            scores.add(v, -DEATH);
        }
        if (k.equals(target.get(v))) {
            scores.add(v, -DEATH);
        }
    }

    @Override
    public void stop(EventContext ctx, boolean awardPrize) {
        Player winner = ctx.playerOf(scores.leader());
        if (awardPrize && winner != null && ctx.relics().mint(prize(), winner)) {
            ctx.broadcast(winner.getName() + " won " + prize().displayName() + "!");
        }
    }

    private void assignTargetsInRing(List<Player> players) {
        if (players.size() < 2) return;
        List<Player> shuffled = new ArrayList<>(players);
        Collections.shuffle(shuffled);
        for (int i = 0; i < shuffled.size(); i++) {
            Player hunter = shuffled.get(i);
            Player bounty = shuffled.get((i + 1) % shuffled.size());
            target.put(hunter.getUniqueId(), bounty.getUniqueId());
        }
    }
}
