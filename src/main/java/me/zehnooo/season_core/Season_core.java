package me.zehnooo.season_core;

import me.zehnooo.season_core.announcement.AnnouncementService;
import me.zehnooo.season_core.command.SeasonCommand;
import me.zehnooo.season_core.listener.PlayerListener;
import me.zehnooo.season_core.season.SeasonConfigManager;
import me.zehnooo.season_core.season.SeasonManager;
import me.zehnooo.season_core.season.SeasonSettings;
import me.zehnooo.season_core.storage.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Season_core extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        SeasonConfigManager configManager = new SeasonConfigManager(this);
        SeasonSettings settings = configManager.load();

        DataManager dataManager = new DataManager(this);
        dataManager.reload();

        SeasonManager seasonManager = new SeasonManager(settings);
        PlayerListener playerListener = new PlayerListener(seasonManager);

        getServer().getPluginManager().registerEvents(playerListener, this);

        SeasonCommand seasonCommand = new SeasonCommand(seasonManager);
        Objects.requireNonNull(getCommand("season")).setExecutor(seasonCommand);

        AnnouncementService announcementService = new AnnouncementService(seasonManager, dataManager);
        getServer().getScheduler().runTaskTimer(this, announcementService::check, 20L, 20L * 60 * 60);

        getLogger().info("Season Core Active...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Season Core Disabled...");
    }
}
