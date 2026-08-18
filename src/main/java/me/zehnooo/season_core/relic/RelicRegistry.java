package me.zehnooo.season_core.relic;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class RelicRegistry {

    private final RelicStore store;
    private final Map<RelicType, Set<UUID>> live = new EnumMap<>(RelicType.class);

    public RelicRegistry(RelicStore store) {
        this.store = store;
        this.live.putAll(store.load());
    }

    public boolean exists(RelicType type) {
        Set<UUID> set = live.get(type);
        return set != null && !set.isEmpty();
    }

    public void register(RelicType type, UUID id) {
        live.computeIfAbsent(type, k -> new HashSet<>()).add(id);
        store.save(live);
    }

    public void markDestroyed(RelicType type, UUID id) {
        Set<UUID> set = live.get(type);
        if (set != null) set.remove(id);
        store.save(live);
    }

    public void clear(RelicType type) {
        live.remove(type);
        store.save(live);
    }
}
