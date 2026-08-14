package me.zehnooo.season_core.listener;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.relic.RelicTarget;
import me.zehnooo.season_core.relic.RelicTrigger;
import me.zehnooo.season_core.relic.RelicType;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;

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
        RelicType type = getMainHandRelic(player);
        if (type == null || type.trigger() != RelicTrigger.RIGHT_CLICK) return;

        LivingEntity victim = type.target() == RelicTarget.VICTIM ? getNearestEntity(player, 10) : null;
        apply(type, player, victim);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;

        Player player;
        boolean fromArrow = false;

        if (event.getDamager() instanceof Player damager) {
            player = damager;
        } else if (event.getDamager() instanceof AbstractArrow arrow && arrow.getShooter() instanceof Player shooter) {
            player = shooter;
            fromArrow = true;
        } else {
            return;
        }

        RelicType type = getMainHandRelic(player);
        if (type == null) return;

        if (type == RelicType.PHASE_BOW) {
            if (!fromArrow) return;
            player.teleportAsync(entity.getLocation());
            return;
        }

        if (type.trigger() == RelicTrigger.CRIT && !event.isCritical()) return;
        if (type.trigger() != RelicTrigger.HIT && type.trigger() != RelicTrigger.CRIT) return;

        apply(type, player, entity);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.isBlocking()) return;

        RelicType type = getOffHandRelic(player);
        if (type == null) type = getMainHandRelic(player);
        if (type != RelicType.THE_WARD) return;

        apply(type, player, null);
    }

    private void apply(RelicType type, Player user, LivingEntity victim) {
        if (type.effect() == null) return;

        LivingEntity target = type.target() == RelicTarget.SELF ? user : victim;
        if (target == null) {
            user.sendMessage("Could not find a target.");
            return;
        }

        target.addPotionEffect(new PotionEffect(
                type.effect(), type.duration(), type.amplifier(), false, true, true));
    }

    private RelicType getOffHandRelic(Player player) {
        return relicManager.getType(player.getInventory().getItemInOffHand());
    }

    private RelicType getMainHandRelic(Player player) {
        return relicManager.getType(player.getInventory().getItemInMainHand());
    }

    private LivingEntity getNearestEntity(Player player, double range) {
        LivingEntity closest = null;
        double closestDist = range * range;
        for (Entity entity : player.getNearbyEntities(range, range, range)) {
            if (!(entity instanceof LivingEntity target) || target.equals(player)) continue;
            double distance = player.getLocation().distanceSquared(target.getLocation());
            if (distance < closestDist) {
                closest = target;
                closestDist = distance;
            }
        }
        return closest;
    }
}
