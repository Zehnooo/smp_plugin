package me.zehnooo.season_core.event;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ScoreTracker {

    private final Map<UUID, Integer> scores = new HashMap<>();

    public void add(UUID player, int amount) {
        scores.merge(player, amount, Integer::sum);
    }

    public int get(UUID player) {
        return scores.getOrDefault(player, 0);
    }

    public UUID leader() {
        UUID best = null;
        int bestScore = Integer.MIN_VALUE;
        for (Map.Entry<UUID, Integer> entry : scores.entrySet()) {
            if (entry.getValue() > bestScore) {
                bestScore = entry.getValue();
                best = entry.getKey();
            }
        }
        return best;
    }

    public Map<UUID, Integer> all() {
        return scores;
    }

    public void reset() {
        scores.clear();
    }
}
