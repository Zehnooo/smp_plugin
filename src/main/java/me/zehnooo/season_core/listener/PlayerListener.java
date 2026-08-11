package me.zehnooo.season_core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public final class PlayerListener implements Listener {

    private boolean isNetherLocked = true;
    private boolean isEndLocked = true;

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
        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        if (cause == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && isNetherLocked) {
            event.setCancelled(true);
            player.sendMessage("You cannot teleport here yet! The Nether Is still sealed...");
        }

        if (cause == PlayerTeleportEvent.TeleportCause.END_PORTAL && isEndLocked) {
            event.setCancelled(true);
            player.sendMessage("You cannot teleport here yet! The End Is still sealed...");
        }
        player.sendMessage("Teleport type: " + cause);
    }
}
