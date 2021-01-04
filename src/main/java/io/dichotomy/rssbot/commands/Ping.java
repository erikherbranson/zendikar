package io.dichotomy.rssbot.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.springframework.stereotype.Component;

@Component
public class Ping implements Command {

    @Override
    public void run(TextChannel channel, String argument) {

        channel.sendMessage("pong!");
    }
}
