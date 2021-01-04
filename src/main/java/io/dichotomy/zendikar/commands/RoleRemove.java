package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleRemove implements Command {

    @Override
    public void run(MessageCreateEvent event, String argument) {

        Server server = event.getServer().get();
        TextChannel channel = event.getChannel();

        List<Role> currentRoles = event.getMessageAuthor().asUser().get().getRoles(server);

        List<Role> rolesToRemove = server.getRolesByNameIgnoreCase(argument);

        if (rolesToRemove.isEmpty()) {

            channel.sendMessage("Cannot find role in server");

            return;
        }

        Role roleToRemove = rolesToRemove.get(0);


        for (Role role : currentRoles) {

            if (role.getIdAsString().equals(roleToRemove.getIdAsString())) {

                roleToRemove.removeUser(event.getMessageAuthor().asUser().get());

                channel.sendMessage("Removed role :thumbsup:");

                return;
            }
        }
    }

}
