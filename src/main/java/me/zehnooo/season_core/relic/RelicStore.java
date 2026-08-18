package me.zehnooo.season_core.relic;

import me.zehnooo.season_core.SeasonCorePlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class RelicStore {

    private final SeasonCorePlugin plugin;
    private final File file;

    public RelicStore(SeasonCorePlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "relics.yml");
    }

    public Map<RelicType, Set<UUID>> load() {
        Map<RelicType, Set<UUID>> result = new EnumMap<>(RelicType.class);
        if (!file.exists()) return result;

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        for (RelicType type : RelicType.values()) {
            List<String> ids = yaml.getStringList(type.name());
            if (ids.isEmpty()) continue;
            Set<UUID> set = new HashSet<>();
            for (String id : ids) {
                try {
                    set.add(UUID.fromString(id));
                } catch (IllegalArgumentException ignored) {
                }
            }
            result.put(type, set);
        }
        return result;
    }

    public void save(Map<RelicType, Set<UUID>> live) {
        YamlConfiguration yaml = new YamlConfiguration();
        for (Map.Entry<RelicType, Set<UUID>> entry : live.entrySet()) {
            List<String> ids = entry.getValue().stream().map(UUID::toString).toList();
            yaml.set(entry.getKey().name(), ids);
        }
        try {
            if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
            yaml.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save relics.yml");
        }
    }
}
