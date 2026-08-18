package me.zehnooo.season_core;

import me.zehnooo.season_core.announcement.AnnouncementService;
import me.zehnooo.season_core.announcement.DataManager;
import me.zehnooo.season_core.event.EventCommand;
import me.zehnooo.season_core.event.EventManager;
import me.zehnooo.season_core.relic.*;
import me.zehnooo.season_core.season.PlayerListener;
import me.zehnooo.season_core.season.SeasonCommand;
import me.zehnooo.season_core.season.SeasonConfigManager;
import me.zehnooo.season_core.season.SeasonManager;
import me.zehnooo.season_core.season.SeasonSettings;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SeasonCorePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // config + season
        SeasonSettings settings = new SeasonConfigManager(this).load();
        SeasonManager seasons = new SeasonManager(settings);

        // relics
        RelicManager relicItems = new RelicManager(this);
        CooldownService cooldowns = new CooldownService();
        RelicStore relicStore = new RelicStore(this);
        RelicRegistry registry = new RelicRegistry(relicStore);
        RelicService relics = new RelicService(relicItems, registry);

        // events
        EventManager events = new EventManager(this, relics);

        // listeners
        register(new PlayerListener(seasons));
        register(new RelicListener(relicItems, cooldowns));
        register(new RelicDestroyListener(relicItems, registry));

        // commands
        setExecutor("season", new SeasonCommand(seasons, relicItems));
        setExecutor("relic", new RelicCommand(relics, relicItems, registry));
        setExecutor("event", new EventCommand(events));

        // scheduled announcements
        DataManager data = new DataManager(this);
        data.reload();
        getServer().getScheduler().runTaskTimer(this, new AnnouncementService(seasons, data)::check, 20L, 20L * 60 * 60);

        getLogger().info("Season Core Active...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Season Core Disabled...");
    }

    private void register(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void setExecutor(String name, CommandExecutor executor) {
        Objects.requireNonNull(getCommand(name)).setExecutor(executor);
    }
}
