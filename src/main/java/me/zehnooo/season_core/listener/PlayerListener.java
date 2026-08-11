package me.zehnooo.season_core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public final class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendMessage("Welcome to Season 1 of KirkCraft!!");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        player.sendMessage("You broke " + event.getBlock().getType().toString());
    }

    @EventHandler
    public void PlayerPortalEvent(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        event.setCancelled(true);
        player.sendMessage("Teleporting is disabled");
    }
}
