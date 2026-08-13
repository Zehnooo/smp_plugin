package me.zehnooo.season_core.relic;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public enum RelicType {

    EMBER_BLADE(Material.NETHERITE_SWORD, "Surtr's Blade", PotionEffectType.FIRE_RESISTANCE, 20 * 6, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF),
    WIND_SPEAR(Material.NETHERITE_SPEAR, "Aelous' Spear", PotionEffectType.SPEED, 20 * 6, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF),
    AXECALIBUR(Material.NETHERITE_AXE, "Perun's Axe", PotionEffectType.WEAKNESS, 20 * 3, 1, RelicTrigger.CRIT, RelicTarget.VICTIM),
    PHASE_BOW(Material.BOW, "Gandiva", null, 0, 0, RelicTrigger.HIT, RelicTarget.SELF),
    HORN_OF_HEALTH(Material.GOAT_HORN, "Horn of Amalthea", PotionEffectType.REGENERATION, 20 * 5, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF),
    THE_WARD(Material.SHIELD, "Aegis", PotionEffectType.RESISTANCE, 20 * 6, 1, RelicTrigger.BLOCK, RelicTarget.SELF),
    SLOWNESS_SHARD(Material.AMETHYST_SHARD, "Ymir's Shard", PotionEffectType.SLOWNESS, 20 * 3, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.VICTIM),
    CLOAK(Material.ECHO_SHARD, "Tarnkappe", PotionEffectType.INVISIBILITY, 20 * 8, 0, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF);

    private final Material material;
    private final String displayName;
    private final PotionEffectType effect;
    private final int duration;
    private final int amplifier;
    private final RelicTrigger trigger;
    private final RelicTarget target;

    RelicType(Material material, String displayName, PotionEffectType effect, int duration, int amplifier, RelicTrigger trigger, RelicTarget target) {
        this.material = material;
        this.displayName = displayName;
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
