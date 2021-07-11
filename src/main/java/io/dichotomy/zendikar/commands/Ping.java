package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class Ping implements MessageCommand {

    @Override
    public void process(MessageCreateEvent event, String argument) {

        event.getChannel().sendMessage("pong!");
    }

    @Override
    public Boolean validateUser(User user, Server server) {

        return true;
    }
}
