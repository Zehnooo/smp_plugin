package me.zehnooo.season_core.listener;

import me.zehnooo.season_core.relic.RelicManager;
import me.zehnooo.season_core.relic.RelicTarget;
import me.zehnooo.season_core.relic.RelicTrigger;
import me.zehnooo.season_core.relic.RelicType;

import org.bukkit.Location;
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
            case RelicTarget.SELF:
                player.addPotionEffect(new PotionEffect(type.effect(), type.duration(), type.amplifier(), false, true, true));
                break;

            case RelicTarget.VICTIM:
                LivingEntity victim = getNearestEntity(player, 5);
                if (victim == null) {
                    player.sendMessage("Could not find a target.");
                    break;
                }
                victim.addPotionEffect(new PotionEffect(type.effect(), type.duration(), type.amplifier(), false, true, true));
                player.sendMessage("Applied effect to " + victim.getName());
                break;
        }

    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player) || !(event.getEntity() instanceof LivingEntity entity)) return;

        RelicType type = checkForRelic(player);
        if (type == null) return;
        if (type.trigger() != RelicTrigger.HIT && type.trigger() != RelicTrigger.CRIT) return;

        switch (type.displayName()){
            case "Gandiva":
                Location loc = getLocation(entity);
                player.teleportAsync(loc)
                        .thenAccept(res -> {
                            if (!res) {
                            player.sendMessage("Could not teleport to entity");
                            } else {
                                player.sendMessage("Teleported to " + entity.getName());
                            }
                        });
                break;

            case "Perun's Axe":
                boolean isCrit = event.isCritical();
                if (!isCrit) break;
                entity.addPotionEffect( new PotionEffect(type.effect(), type.duration(), type.amplifier(), false, true, true));
                break;
        }
    }

    private RelicType checkForRelic(Player player) {
        return relicManager.getType(player.getInventory().getItemInMainHand());
    }

    private Location getLocation(Entity entity) {
        return new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
    }

    private LivingEntity getNearestEntity(Player player, double range){
        LivingEntity closest = null;
        double area = range * range;
        for (Entity entity : player.getNearbyEntities(range, range, range)) {
            if (entity instanceof LivingEntity target) {
                double distance = player.getLocation().distanceSquared(entity.getLocation());
                if (distance < area && (closest == null || target.getLocation().distanceSquared(player.getLocation()) < closest.getLocation().distanceSquared(player.getLocation()))) {
                    closest = target;
                }
            }
        }
        return closest;
    }

}
