package me.zehnooo.season_core.listener;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.relic.RelicType;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class RelicListener implements Listener {
    private final RelicManager relicManager;

    public RelicListener(RelicManager relicManager) {
        this.relicManager = relicManager;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        player.sendMessage("right click received");
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (!relicManager.isRelic(hand)) return;
        player.sendMessage(relicManager.getType(hand).toString());
    }
}
