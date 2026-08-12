package me.zehnooo.season_core.announcement;

import me.zehnooo.season_core.season.SeasonManager;
import me.zehnooo.season_core.storage.DataManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public final class AnnouncementService {

    private final SeasonManager seasons;
    private final DataManager data;

    public AnnouncementService(SeasonManager seasons, DataManager data) {
        this.seasons = seasons;
        this.data = data;
    }

    public void check() {
        //Nether Messages
        Bukkit.broadcast(Component.text("Test Message"));
        trySend("nether", "unlock", seasons.daysUntilNetherUnlock() == 0);
        trySend("nether", "one-week-reminder", seasons.daysUntilNetherUnlock() == 7);
        trySend("nether", "one-day-reminder", seasons.daysUntilNetherUnlock() == 1);
        //End Messages
        trySend("end", "unlock", seasons.daysUntilEndUnlock() == 0);
        trySend("end", "one-week-reminder", seasons.daysUntilEndUnlock() == 7);
        trySend("end", "one-day-reminder", seasons.daysUntilEndUnlock() == 1);
        //Season Messages
        trySend("season", "one-day-reminder", seasons.daysUntilSeasonEnd() == 1);
        trySend("season", "one-week-reminder", seasons.daysUntilSeasonEnd() == 7);
        trySend("season", "end", seasons.daysUntilSeasonEnd() == 0);
    }

    private void trySend(String phase, String event, boolean due) {
        if (!due || data.isSent(phase, event)) return;
        String msg = data.getMessage(phase, event);
        if (msg == null || msg.isBlank()) return;
        Bukkit.broadcast(Component.text(msg));
        data.setSent(phase, event, true);
    }

}
