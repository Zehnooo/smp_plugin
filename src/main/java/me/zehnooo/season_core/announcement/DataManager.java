package me.zehnooo.season_core.announcement;

import java.io.File;

import me.zehnooo.season_core.SeasonCorePlugin;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
    private final SeasonCorePlugin plugin;
    private final File file;
    private YamlConfiguration data;

    public DataManager(SeasonCorePlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "data.yml");
    }

    public void reload() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        if (!file.exists()) {
            plugin.saveResource("data.yml", false);
        }
        data = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            data.save(file);
        } catch (java.io.IOException e) {
            plugin.getLogger().severe("Could not save data to data.yml");
            e.printStackTrace();
        }
    }

    private static final String DATA_PREFIX = "announcements.";
    private static final String MSG_SUFFIX = ".message";
    private static final String SENT_SUFFIX = ".sent";

    public String getMessage(String phase, String event) {
        return data.getString(DATA_PREFIX + phase + "." + event + MSG_SUFFIX);
    }

    public boolean isSent(String phase, String event) {
        return data.getBoolean(DATA_PREFIX + phase + "." + event + SENT_SUFFIX);
    }

    public void setSent(String phase, String event, boolean value) {
        data.set(DATA_PREFIX + phase + "." + event + SENT_SUFFIX, value);
        save();
    }

}
