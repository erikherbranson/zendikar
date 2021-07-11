package io.dichotomy.zendikar.commands;

import org.javacord.api.event.message.MessageCreateEvent;

public interface MessageCommand {

    void process(MessageCreateEvent event, String argument);
}
