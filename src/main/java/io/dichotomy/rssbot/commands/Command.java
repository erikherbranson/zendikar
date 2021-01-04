package io.dichotomy.rssbot.commands;

import org.javacord.api.entity.channel.TextChannel;

public interface Command {

    void run(TextChannel channel, String argument);
}
