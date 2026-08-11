package me.zehnooo.season_core;

import me.zehnooo.season_core.listener.PlayerListener;
import me.zehnooo.season_core.season.SeasonManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Season_core extends JavaPlugin {

    @Override
    public void onEnable() {
        SeasonManager seasonManager = new SeasonManager();
        PlayerListener playerListener = new PlayerListener(seasonManager);

        getServer().getPluginManager().registerEvents(playerListener, this);

        getLogger().info("Season Core Active...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Season Core Disabled...");
    }
}
