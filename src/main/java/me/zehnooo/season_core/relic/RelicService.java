package me.zehnooo.season_core.relic;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class RelicService {

    private final RelicManager items;
    private final RelicRegistry registry;

    public RelicService(RelicManager items, RelicRegistry registry) {
        this.items = items;
        this.registry = registry;
    }

    public boolean mint(RelicType type, Player to) {
        if (registry.exists(type)) return false;
        ItemStack relic = items.create(type);
        registry.register(type, items.getId(relic));
        to.getInventory().addItem(relic);
        return true;
    }
}
