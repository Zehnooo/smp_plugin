package me.zehnooo.season_core.event;

import me.zehnooo.season_core.relic.RelicService;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class EventContext {

    private final RelicService relics;

    public EventContext(RelicService relics) {
        this.relics = relics;
    }

    public RelicService relics() {
        return relics;
    }

    public List<Player> players() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    public Player playerOf(UUID id) {
        return id == null ? null : Bukkit.getPlayer(id);
    }

    public void broadcast(String message) {
        Bukkit.broadcast(Component.text(message));
    }
}
