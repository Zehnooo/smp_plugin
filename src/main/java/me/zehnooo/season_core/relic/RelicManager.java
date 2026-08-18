package me.zehnooo.season_core.relic;

import me.zehnooo.season_core.SeasonCorePlugin;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;
import java.util.UUID;

public final class RelicManager {

    private final NamespacedKey typeKey;
    private final NamespacedKey uuidKey;
    private final NamespacedKey arrowKey;

    public RelicManager(SeasonCorePlugin plugin) {
        this.typeKey = new NamespacedKey(plugin, "relic_type");
        this.uuidKey = new NamespacedKey(plugin, "relic_uuid");
        this.arrowKey = new NamespacedKey(plugin, "relic_arrow");
    }

    public ItemStack create(RelicType type) {
        ItemStack item = new ItemStack(type.material());
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(type.displayName(), NamedTextColor.RED));
        meta.lore(List.of(Component.text(type.description(), NamedTextColor.GOLD)));
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(typeKey, PersistentDataType.STRING, type.name());
        data.set(uuidKey, PersistentDataType.STRING, UUID.randomUUID().toString());
        meta.setEnchantmentGlintOverride(true);

        item.setItemMeta(meta);
        return item;
    }

    public boolean isRelic(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(typeKey, PersistentDataType.STRING);
    }

    public RelicType getType(ItemStack item) {
        String raw = read(item, typeKey);
        if (raw == null) return null;
        try {
            return RelicType.valueOf(raw);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public UUID getId(ItemStack item) {
        String raw = read(item, uuidKey);
        if (raw == null) return null;
        try {
            return UUID.fromString(raw);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    private String read(ItemStack item, NamespacedKey key) {
        if (!isRelic(item)) return null;
        return item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    public void markPhaseArrow(AbstractArrow arrow) {
        arrow.getPersistentDataContainer().set(arrowKey, PersistentDataType.STRING, RelicType.PHASE_BOW.name());
    }

    public boolean isPhaseArrow(AbstractArrow arrow) {
        String raw = arrow.getPersistentDataContainer().get(arrowKey, PersistentDataType.STRING);
        return RelicType.PHASE_BOW.name().equals(raw);
    }
}
