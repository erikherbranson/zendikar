package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.channel.TextChannel;

public interface Command {

    void run(TextChannel channel, String argument);
}
