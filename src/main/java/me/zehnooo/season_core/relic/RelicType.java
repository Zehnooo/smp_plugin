package me.zehnooo.season_core.relic;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public enum RelicType {

    EMBER_BLADE(Material.NETHERITE_SWORD, "Surtr's Blade", "Right click grants the user temporary fire resistance.", PotionEffectType.FIRE_RESISTANCE, 20 * 6, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF),
    WIND_SPEAR(Material.NETHERITE_SPEAR, "Aelous' Spear", "Right click grants the user temporary speed.", PotionEffectType.SPEED, 20 * 6, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF),
    AXECALIBUR(Material.NETHERITE_AXE, "Perun's Axe", "Landing a crit grants the target temporary weakness.", PotionEffectType.WEAKNESS, 20 * 3, 1, RelicTrigger.CRIT, RelicTarget.VICTIM),
    PHASE_BOW(Material.BOW, "Gandiva", "Hitting an entity with an arrow will teleport you to that entity", null, 0, 0, RelicTrigger.HIT, RelicTarget.SELF),
    HORN_OF_HEALTH(Material.GOAT_HORN, "Horn of Amalthea", "Activating this item grants the user temporary regeneration.", PotionEffectType.REGENERATION, 20 * 5, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF),
    THE_WARD(Material.SHIELD, "Aegis", "Successfully blocking an attack grants the user temporary resistance.", PotionEffectType.RESISTANCE, 20 * 6, 1, RelicTrigger.BLOCK, RelicTarget.SELF),
    SLOWNESS_SHARD(Material.AMETHYST_SHARD, "Ymir's Shard", "Activating this item grants the nearest entity temporary slowness.", PotionEffectType.SLOWNESS, 20 * 3, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.VICTIM),
    CLOAK(Material.ECHO_SHARD, "Tarnkappe", "Activating this item grants the user temporary invisibility", PotionEffectType.INVISIBILITY, 20 * 8, 0, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF);

    private final Material material;
    private final String displayName;
    private final String description;
    private final PotionEffectType effect;
    private final int duration;
    private final int amplifier;
    private final RelicTrigger trigger;
    private final RelicTarget target;

    RelicType(Material material, String displayName, String description, PotionEffectType effect, int duration, int amplifier, RelicTrigger trigger, RelicTarget target) {
        this.material = material;
        this.displayName = displayName;
        this.description = description;
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
        this.trigger = trigger;
        this.target = target;
    }

    public Material material() {
        return material;
    }
    public String displayName() {
        return displayName;
    }
    public String description() { return description; }
    public PotionEffectType effect() {
        return effect;
    }
    public int duration() {
        return duration;
    }
    public int amplifier() {
        return amplifier;
    }
    public RelicTrigger trigger() {
        return trigger;
    }
    public RelicTarget target() {
        return target;
    }

}
