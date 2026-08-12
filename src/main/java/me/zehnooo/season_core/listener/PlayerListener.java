package me.zehnooo.season_core.listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
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
        final long seasonDay = seasonManager.getSeasonDay();
        final long daysUntilNetherUnlock = seasonManager.daysUntilNetherUnlock();
        final long daysUntilEndUnlock = seasonManager.daysUntilEndUnlock();
        final long daysUntilSeasonEnd = seasonManager.daysUntilSeasonEnd();

        player.sendMessage("Welcome to Season 1 of KirkCraft!!");
        player.sendMessage("Season Day: " + seasonDay);
        if (daysUntilNetherUnlock > 0) player.sendMessage("Nether unlocks in " + daysUntilNetherUnlock + "days");
        if (daysUntilEndUnlock > 0) player.sendMessage("End unlocks in " + daysUntilEndUnlock + "days");
        player.sendMessage("Season ends in " + daysUntilSeasonEnd + "days");

    }

    /*
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        player.sendMessage("You broke " + event.getBlock().getType().toString());
    }
     */

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){

        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();
        Location from = event.getFrom();
        World to = event.getTo().getWorld();
        if (to == null) { return; }
        World.Environment env = to.getEnvironment();

        if ( env == World.Environment.NETHER && seasonManager.isNetherLocked() ) {

            event.setCancelled(true);
            player.sendMessage("You cannot teleport here yet! The Nether Is still sealed...");
        }

        if ( env == World.Environment.THE_END && seasonManager.isEndLocked()) {
            event.setCancelled(true);
            player.sendMessage("You cannot teleport here yet! The End Is still sealed...");
        }
        player.sendMessage("Teleport type: " + cause);
    }

}
