package me.zehnooo.season_core.relic;

import org.bukkit.entity.*;
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
    private final CooldownService cooldowns;

    public RelicListener(RelicManager items, CooldownService cooldowns) {
        this.relicManager = items;
        this.cooldowns = cooldowns;
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

        if (event.getDamager() instanceof AbstractArrow arrow && arrow.getShooter() instanceof Player shooter && relicManager.isPhaseArrow(arrow)) {
            if (!consume(shooter, RelicType.PHASE_BOW)) return;
            shooter.teleportAsync(entity.getLocation());
            return;
        }

        if (!(event.getDamager() instanceof Player player)) return;
        ItemStack item = mainHand(player);
        RelicType type = relicManager.getType(item);

        if (type == null) return;
        if (type.trigger() == RelicTrigger.CRIT && !event.isCritical()) return;
        if (type.trigger() != RelicTrigger.HIT && type.trigger() != RelicTrigger.CRIT) return;

        apply(item, player, entity);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.isBlocking()) return;
        if (!(event.getDamage() <= 0)) return;

        ItemStack item = findWard(player);
        if (item == null) return;

        apply(item, player, null);
    }

    @EventHandler
    public void onBowFire(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (relicManager.getType(event.getBow()) != RelicType.PHASE_BOW) return;
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            relicManager.markPhaseArrow(arrow);
        }
    }

    private void apply(ItemStack item, Player user, LivingEntity victim) {
        RelicType type = relicManager.getType(item);
        if (type == null || type.effect() == null) return;

        LivingEntity target = type.target() == RelicTarget.SELF ? user : victim;
        if (target == null) {
            user.sendMessage("Could not find a target.");
            return;
        }
        if (target == victim && victim instanceof Tameable tameable && tameable.getOwner() != null) { return; }
        if (target == victim && victim instanceof ArmorStand) { return; }
        if (!consume(user, type)) return;

        target.addPotionEffect(new PotionEffect(
                type.effect(), type.duration(), type.amplifier(), false, true, true));
    }

    private boolean consume(Player user, RelicType type) {
        if (cooldowns.ready(user, type)) { cooldowns.trigger(user, type); return true; }
        user.sendMessage("On cooldown: " + cooldowns.remainingSeconds(user, type) + "s");
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
