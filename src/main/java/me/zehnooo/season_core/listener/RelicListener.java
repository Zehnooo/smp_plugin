package me.zehnooo.season_core.listener;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.relic.RelicTarget;
import me.zehnooo.season_core.relic.RelicTrigger;
import me.zehnooo.season_core.relic.RelicType;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public final class RelicListener implements Listener {

    private static final double NEAREST_RANGE = 10;

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
        ItemStack item = mainHand(player);
        RelicType type = relicManager.getType(item);
        if (type == null || type.trigger() != RelicTrigger.RIGHT_CLICK) return;

        LivingEntity victim = type.target() == RelicTarget.VICTIM ? nearestLiving(player, NEAREST_RANGE) : null;
        apply(item, player, victim);
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

        ItemStack item = mainHand(player);
        RelicType type = relicManager.getType(item);
        if (type == null) return;

        if (type == RelicType.PHASE_BOW) {
            if (!fromArrow) return;
            if (!consume(item, player)) return;
            player.teleportAsync(entity.getLocation());
            return;
        }

        if (type.trigger() == RelicTrigger.CRIT && !event.isCritical()) return;
        if (type.trigger() != RelicTrigger.HIT && type.trigger() != RelicTrigger.CRIT) return;

        apply(item, player, entity);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.isBlocking()) return;

        ItemStack item = findWard(player);
        if (item == null) return;

        apply(item, player, null);
    }

    @EventHandler
    public void onBowFire(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

    }

    private void apply(ItemStack item, Player user, LivingEntity victim) {
        RelicType type = relicManager.getType(item);
        if (type == null || type.effect() == null) return;

        LivingEntity target = type.target() == RelicTarget.SELF ? user : victim;
        if (target == null) {
            user.sendMessage("Could not find a target.");
            return;
        }
        if (!consume(item, user)) return;

        target.addPotionEffect(new PotionEffect(
                type.effect(), type.duration(), type.amplifier(), false, true, true));
    }

    private boolean consume(ItemStack item, Player user) {
        if (relicManager.tryUse(item)) return true;
        user.sendMessage("On cooldown: " + relicManager.remainingSeconds(item) + "s");
        return false;
    }

    private ItemStack findWard(Player player) {
        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (relicManager.getType(offHand) == RelicType.THE_WARD) return offHand;
        ItemStack main = mainHand(player);
        if (relicManager.getType(main) == RelicType.THE_WARD) return main;
        return null;
    }

    private ItemStack mainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    private LivingEntity nearestLiving(Player player, double range) {
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
