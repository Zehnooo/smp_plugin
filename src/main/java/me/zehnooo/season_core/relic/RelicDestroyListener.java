package me.zehnooo.season_core.relic;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public final class RelicDestroyListener implements Listener {

    private final RelicManager relicManager;
    private final RelicRegistry registry;

    public RelicDestroyListener(RelicManager relicManager, RelicRegistry registry) {
        this.relicManager = relicManager;
        this.registry = registry;
    }

    @EventHandler
    public void onItemDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Item itemEntity)) return;
        ItemStack stack = itemEntity.getItemStack();
        RelicType type = relicManager.getType(stack);
        if (type == null) return;

        switch (event.getCause()) {
            case LAVA, FIRE, FIRE_TICK, BLOCK_EXPLOSION, ENTITY_EXPLOSION, VOID -> {
                registry.markDestroyed(type, relicManager.getId(stack));
                Bukkit.broadcast(Component.text(type.displayName() + " was destroyed! It can be won again."));
            }
            default -> {
            }
        }
    }
}
