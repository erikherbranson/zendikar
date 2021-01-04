package io.dichotomy.zendikar.commands;

import org.javacord.api.event.message.MessageCreateEvent;

public interface Command {

    void run(MessageCreateEvent event, String argument);
}
