package me.zehnooo.season_core.relic;

import org.bukkit.entity.Player;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CooldownService {

    private final Map<UUID, Map<RelicType, Long>> readyAt = new HashMap<>();

    public boolean ready(Player player, RelicType type) {
        long until = readyAt.getOrDefault(player.getUniqueId(), Map.of()).getOrDefault(type, 0L);
        return System.currentTimeMillis() >= until;
    }

    public void trigger(Player player, RelicType type) {
        readyAt.computeIfAbsent(player.getUniqueId(), k -> new EnumMap<>(RelicType.class))
                .put(type, System.currentTimeMillis() + type.cooldown() * 50L);
    }

    public long remainingSeconds(Player player, RelicType type) {
        long until = readyAt.getOrDefault(player.getUniqueId(), Map.of()).getOrDefault(type, 0L);
        return Math.max(0, (until - System.currentTimeMillis() + 999) / 1000);
    }
}
