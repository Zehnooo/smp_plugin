package me.zehnooo.season_core;

import me.zehnooo.season_core.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Season_core extends JavaPlugin {

    @Override
    public void onEnable() {
        PlayerListener playerListener = new PlayerListener();
        getServer().getPluginManager().registerEvents(playerListener, this);

        getLogger().info("Season Core Active...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Season Core Disabled...");
    }
}
