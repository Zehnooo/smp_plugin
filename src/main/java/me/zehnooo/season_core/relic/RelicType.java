package me.zehnooo.season_core.relic;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public enum RelicType {


    /*
    NAME(Material.ITEM, "")
    Emberblade - Right Click gives fire resistance for 6 seconds. Successful crit applies fire aspect to hit entity for 3 seconds
    Windspear - Right click grants user speed 2 for 6 seconds
    Axe of Crippling - Successful crit applies weakness to hit entity for 3 seconds
    Phase Bow - Successful hit teleports user to target
    Horn of Health - Gives regen to user for 5 seconds
    The Ward - Blocking gives resistance for 6 seconds
    Frost Shard - Applies freezing to nearby enemies
    Cloak - Gives user invisibility for 8 seconds. Attacking or taking damage removes invis
     */

    EMBER_BLADE(Material.NETHERITE_SWORD, "Ember Blade", PotionEffectType.FIRE_RESISTANCE, 20 * 6, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF),
    WIND_SPEAR(Material.NETHERITE_SPEAR, "Wind Spear", PotionEffectType.SPEED, 20 * 6, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF),
    WAR_AXE(Material.NETHERITE_AXE, "War Axe", PotionEffectType.WEAKNESS, 20 * 3, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.VICTIM),
    PHASE_BOW(Material.BOW, "Phase Bow", null, 0, 0, RelicTrigger.HIT, RelicTarget.SELF),
    HORN_OF_HEALTH(Material.GOAT_HORN, "Horn of Health", PotionEffectType.REGENERATION, 20 * 5, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.TEAM),
    THE_WARD(Material.SHIELD, "The Ward", PotionEffectType.RESISTANCE, 20 * 6, 1, RelicTrigger.BLOCK, RelicTarget.SELF),
    SLOWNESS_SHARD(Material.AMETHYST_SHARD, "Slowness Shard", PotionEffectType.SLOWNESS, 20 * 3, 1, RelicTrigger.RIGHT_CLICK, RelicTarget.VICTIM),
    CLOAK(Material.ECHO_SHARD, "Cloak", PotionEffectType.INVISIBILITY, 20 * 8, 0, RelicTrigger.RIGHT_CLICK, RelicTarget.SELF);

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
