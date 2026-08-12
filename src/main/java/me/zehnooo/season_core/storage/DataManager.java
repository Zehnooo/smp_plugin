package me.zehnooo.season_core.storage;
import java.io.File;

import me.zehnooo.season_core.Season_core;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
    private final Season_core plugin;
    private final File file;
    private YamlConfiguration data;

    public DataManager(Season_core plugin) {
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

    public boolean isNetherMsgSent() {
        return data.getBoolean("announcements.nether-msg-sent");
    }
    public void setNetherMsgSent(boolean value) {
        data.set("announcements.nether-msg-sent", value);
    }


}
