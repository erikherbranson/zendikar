package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public interface MessageCommand {

    void process(MessageCreateEvent event, String argument);

    Boolean validateUser(User user, Server server);
}
