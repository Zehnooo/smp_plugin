package me.zehnooo.season_core.relic;

import org.bukkit.Material;

public enum RelicType {


    /*
    NAME(Material.ITEM, "")
    Emberblade - Right Click gives fire resistance for 6 seconds. Successful crit applies fire aspect to hit entity for 3 seconds
    Windspear - Right click grants user speed 2 for 6 seconds
    Axe of Crippling - Successful crit applies slowness to hit entity for 4 seconds
    Phase Bow - Successful hit teleports user to target
    Horn of Health - Gives regen to user for 5 seconds
    The Ward - Blocking gives resistance for 6 seconds
    Frost Shard - Applies freezing to nearby enemies
    Cloak - Gives user invisibility for 8 seconds. Attacking or taking damage removes invis
     */

    EMBER_BLADE(Material.NETHERITE_SWORD, "Ember Blade"),
    WIND_SPEAR(Material.NETHERITE_SPEAR, "Wind Spear"),
    AXE_OF_CRIPPLING(Material.NETHERITE_AXE, "Axe of Crippling"),
    PHASE_BOW(Material.BOW, "Phase Bow"),
    HORN_OF_HEALTH(Material.GOAT_HORN, "Horn of Health"),
    THE_WARD(Material.SHIELD, "The Ward"),
    FROST_SHARD(Material.AMETHYST_SHARD, "Frost Shard"),
    CLOAK(Material.ECHO_SHARD, "Cloak");

    private final Material material;
    private final String displayName;

    RelicType(Material material, String displayName) {
        this.material = material;
        this.displayName = displayName;
    }

    public Material material() {
        return material;
    }

    public String displayName() {
        return displayName;
    }

}
