package me.zehnooo.season_core.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public final class Msg {

    private Msg() {
    }

    public static void info(CommandSender to, String text) {
        to.sendMessage(Component.text(text, NamedTextColor.GRAY));
    }

    public static void error(CommandSender to, String text) {
        to.sendMessage(Component.text(text, NamedTextColor.RED));
    }
}
