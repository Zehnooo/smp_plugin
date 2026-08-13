package me.zehnooo.season_core.listener;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.relic.RelicTarget;
import me.zehnooo.season_core.relic.RelicTrigger;
import me.zehnooo.season_core.relic.RelicType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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

        RelicType type = checkForRelic(player);

        if (type == null || type.effect() == null) return;
        if (type.trigger() != RelicTrigger.RIGHT_CLICK) return;

        switch (type.target()){
            case type.target() == RelicTarget.SELF
                player.addPotionEffect(new PotionEffect(type.effect(), type.duration(), type.amplifier(), false, true, true));
                break;
        }

    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player) || !(event.getEntity() instanceof LivingEntity entity)) return;
        boolean isCrit = event.isCritical();
        RelicType type = checkForRelic(player);
        if (type == null) return;
        if (type.trigger() != RelicTrigger.HIT && type.trigger() != RelicTrigger.CRIT) return;
        if (type.trigger() == RelicTrigger.CRIT && isCrit && type.target() == RelicTarget.VICTIM ) {
            entity.addPotionEffect( new PotionEffect(type.effect(), type.duration(), type.amplifier(), false, true, true));
        }
        player.sendMessage("Hit: " + isCrit + " vs " + entity);
    }

    private RelicType checkForRelic(Player player) {
        return relicManager.getType(player.getInventory().getItemInMainHand());
    }

}
