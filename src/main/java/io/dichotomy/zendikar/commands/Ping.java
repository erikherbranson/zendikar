package io.dichotomy.zendikar.commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class Ping implements Command {

    @Override
    public void run(MessageCreateEvent event, String argument) {

        event.getChannel().sendMessage("pong!");
    }
}
