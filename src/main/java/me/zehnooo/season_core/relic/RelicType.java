package me.zehnooo.season_core.relic;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public enum RelicType {

    EMBER_BLADE(Material.NETHERITE_SWORD, "Surtr's Sword", "Right click grants the user temporary fire resistance.", PotionEffectType.FIRE_RESISTANCE, 20 * 6, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF, 20 * 20),
    WIND_SPEAR(Material.NETHERITE_SPEAR, "Aeglos", "Right click grants the user temporary speed.", PotionEffectType.SPEED, 20 * 6, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF, 20 * 20),
    AXECALIBUR(Material.NETHERITE_AXE, "Axe of Perun", "Landing a crit grants the target temporary weakness.", PotionEffectType.WEAKNESS, 20 * 3, 1, RelicTrigger.CRIT, RelicTarget.VICTIM, 20 * 20),
    PHASE_BOW(Material.BOW, "Gandiva", "Hitting an entity with an arrow will teleport you to that entity.", null, 0, 0, RelicTrigger.HIT, RelicTarget.SELF, 20 * 20),
    HORN_OF_HEALTH(Material.GOAT_HORN, "Horn of Plenty", "Activating this item grants the user temporary absorption.", PotionEffectType.ABSORPTION, 20 * 5, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF, 20 * 20),
    THE_WARD(Material.SHIELD, "Aegis", "Successfully blocking an attack grants the user temporary resistance.", PotionEffectType.RESISTANCE, 20 * 6, 1, RelicTrigger.BLOCK, RelicTarget.SELF, 20 * 20),
    SLOWNESS_SHARD(Material.AMETHYST_SHARD, "Ymir's Shard", "Activating this item grants the nearest entity temporary slowness.", PotionEffectType.SLOWNESS, 20 * 3, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.VICTIM, 20 * 20),
    CLOAK(Material.ECHO_SHARD, "Tarnkappe", "Activating this item grants the user temporary invisibility.", PotionEffectType.INVISIBILITY, 20 * 8, 0, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF, 20 * 20);

    private final Material material;
    private final String displayName;
    private final String description;
    private final PotionEffectType effect;
    private final int duration;
    private final int amplifier;
    private final RelicTrigger trigger;
    private final RelicTarget target;
    private final int cooldown;

    RelicType(Material material, String displayName, String description, PotionEffectType effect, int duration, int amplifier, RelicTrigger trigger, RelicTarget target, int cooldown) {
        this.material = material;
        this.displayName = displayName;
        this.description = description;
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
        this.trigger = trigger;
        this.target = target;
        this.cooldown = cooldown;
    }

    public static RelicType randomPrize(){
        return values()[(int)(Math.random() * values().length)];
    }

    public Material material() {
        return material;
    }
    public String displayName() {
        return displayName;
    }
    public String description() {
        return description;
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

    public int cooldown() {
        return cooldown;
    }

}
