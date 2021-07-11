package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleAdd implements MessageCommand {

    @Override
    public void process(MessageCreateEvent event, String argument) {

        Server server = event.getServer().get();

        List<Role> roles = server.getRolesByNameIgnoreCase(argument);

        User user = event.getMessageAuthor().asUser().get();

        if (roles.isEmpty() || argument.equalsIgnoreCase("admin")) {

            errorResponse(user, argument, event.getChannel());

        } else {

            Role role = roles.get(0);

            role.addUser(user);

            successResponse(user, role, event.getChannel());
        }
    }

    private void errorResponse(User user, String argument, TextChannel channel) {

        String errorMessage = String.format(":x: **%s** cannot be assigned role **_%s_** ", user.getName(), argument);

        errorResponse(channel, errorMessage);

    }

    private void errorResponse(TextChannel channel, String message) {

        channel.sendMessage(message);
    }

    private void successResponse(User user, Role role, TextChannel channel) {

        String message = String.format(":white_check_mark: Assigned role **_%s_** to **%s**", role.getName(), user.getName());

        successResponse(channel, message);
    }

    private void successResponse(TextChannel channel, String message) {

        channel.sendMessage(message);
    }

}
