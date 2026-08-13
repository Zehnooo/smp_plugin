package me.zehnooo.season_core.listener;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.relic.RelicType;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.EquipmentSlot;
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
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();

        ItemStack hand = player.getInventory().getItemInMainHand();
        if (!relicManager.isRelic(hand)) return;

        RelicType type = relicManager.getType(hand);
        if (type == null || type.effect() == null) return;
        player.addPotionEffect(new PotionEffect(type.effect(), type.duration(), type.amplifier(), false, true, true));
    }
}
