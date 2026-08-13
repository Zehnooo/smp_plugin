package me.zehnooo.season_core.relic;

import me.zehnooo.season_core.Season_core;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import net.kyori.adventure.text.Component;
import java.util.UUID;

public class RelicManager {

    private final NamespacedKey typeKey;
    private final NamespacedKey uuidKey;

    public RelicManager(Season_core plugin) {
        this.typeKey = new NamespacedKey(plugin, "relic_type");
        this.uuidKey = new NamespacedKey(plugin, "relic_uuid");
    }

    public ItemStack create(RelicType type) {
        ItemStack item = new ItemStack(type.material());
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(type.displayName()));
        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, type.name());
        meta.getPersistentDataContainer().set(uuidKey, PersistentDataType.STRING, UUID.randomUUID().toString());

        item.setItemMeta(meta);
        return item;
    }

}
