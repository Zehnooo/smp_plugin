package me.zehnooo.season_core.listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import me.zehnooo.season_core.season.SeasonManager;

public final class PlayerListener implements Listener {

    private final SeasonManager seasonManager;

    public PlayerListener(SeasonManager seasonManager) {
        this.seasonManager = seasonManager;
    }

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
    public void onPlayerTeleport(PlayerTeleportEvent event){
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();
        Location from = event.getFrom();
        Location to = event.getTo();

        if ( to.getWorld().getEnvironment() == World.Environment.NETHER && seasonManager.isNetherLocked() ) {
            event.setCancelled(true);
            player.sendMessage("You cannot teleport here yet! The Nether Is still sealed...");
        }

        if ( to.getWorld().getEnvironment() == World.Environment.THE_END && seasonManager.isEndLocked()) {
            event.setCancelled(true);
            player.sendMessage("You cannot teleport here yet! The End Is still sealed...");
        }
        player.sendMessage("Teleport type: " + cause);
    }

}
