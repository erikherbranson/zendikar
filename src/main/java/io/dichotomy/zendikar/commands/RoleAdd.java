package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleAdd implements Command {

    @Override
    public void run(MessageCreateEvent event, String argument) {

        Server server = event.getServer().get();

        List<Role> roles = server.getRolesByNameIgnoreCase(argument);

        String userName = event.getMessageAuthor().getName();

        if (roles.isEmpty() || argument.equalsIgnoreCase("admin")) {

            event.getChannel().sendMessage(String.format("I cannot do that %s", userName));

        } else {

            Role role = roles.get(0);

            role.addUser(event.getMessageAuthor().asUser().get());

            event.getChannel().sendMessage(String.format("Added the role %s to user %s", role.getName(), userName ));

        }
    }
}
