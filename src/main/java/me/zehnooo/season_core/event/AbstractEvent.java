package me.zehnooo.season_core.event;

public abstract class AbstractEvent implements SeasonEvent {

    protected final ScoreTracker scores = new ScoreTracker();
}
