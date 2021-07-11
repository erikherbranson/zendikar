package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleRemove implements MessageCommand {

    @Override
    public void process(MessageCreateEvent event, String argument) {

        Server server = event.getServer().get();
        TextChannel channel = event.getChannel();

        List<Role> currentRoles = event.getMessageAuthor().asUser().get().getRoles(server);

        List<Role> rolesToRemove = server.getRolesByNameIgnoreCase(argument);

        if (rolesToRemove.isEmpty()) {

            channel.sendMessage(String.format(":x: Cannot find role **_%s_** in server", argument));

            return;
        }

        Role roleToRemove = rolesToRemove.get(0);


        for (Role role : currentRoles) {

            if (role.getIdAsString().equals(roleToRemove.getIdAsString())) {

                roleToRemove.removeUser(event.getMessageAuthor().asUser().get());

                String message = String.format(":white_check_mark: Removed role **_%s_**", roleToRemove.getName());

                channel.sendMessage(message);

                return;
            }
        }
    }

}
