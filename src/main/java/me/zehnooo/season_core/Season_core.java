package me.zehnooo.season_core;

import me.zehnooo.season_core.command.SeasonCommand;
import me.zehnooo.season_core.listener.PlayerListener;
import me.zehnooo.season_core.season.SeasonConfigManager;
import me.zehnooo.season_core.season.SeasonManager;
import me.zehnooo.season_core.season.SeasonSettings;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Season_core extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        SeasonConfigManager configManager = new SeasonConfigManager(this);
        SeasonManager seasonManager = new SeasonManager(configManager.load());
        PlayerListener playerListener = new PlayerListener(seasonManager);

        getServer().getPluginManager().registerEvents(playerListener, this);

        SeasonCommand seasonCommand = new SeasonCommand(seasonManager);
        Objects.requireNonNull(getCommand("season")).setExecutor(seasonCommand);

        getLogger().info("Season Core Active...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Season Core Disabled...");
    }
}
