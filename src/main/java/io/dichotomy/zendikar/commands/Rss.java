package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Rss implements MessageCommand {

    @Override
    public void process(MessageCreateEvent event, String argument) {

        processAction(getActionType(argument), getRoleName(argument), event);
    }

    @Override
    public Boolean validateUser(User user, Server server) {

        return true;
    }

    private void processAction(String action, String roleName, MessageCreateEvent event) {

        switch (action) {

            case "--list":
                listSubscriptions(event);
                break;

            case "--sub":
                subscribeUser(roleName, event);
                break;

            case "--rm":
                unsubscribeUser(roleName, event);
                break;

            default:
                displayOptions(event);
        }
    }

    private void subscribeUser(String roleName, MessageCreateEvent event) {

        Server server = event.getServer().get();

        if (! roleName.startsWith("rss")) {

            event.getChannel().sendMessage("Rss channels must begin with rss");
            return;
        }

        List<Role> roles = server.getRolesByNameIgnoreCase(roleName);

        User user = event.getMessageAuthor().asUser().get();

        if (roles.isEmpty()) {

            event.getChannel().sendMessage(":x: 404");

        } else {

            Role role = roles.get(0);

            role.addUser(user);

            String message = String.format(":white_check_mark:  now subscribed to channel **_%s_**", role.getName());

            event.getChannel().sendMessage(message);
        }
    }

    private void unsubscribeUser(String roleName, MessageCreateEvent event) {

        Server server = event.getServer().get();
        TextChannel channel = event.getChannel();

        if (! roleName.startsWith("rss")) {

            event.getChannel().sendMessage("Rss channels must begin with rss");
            return;
        }

        List<Role> currentRoles = event.getMessageAuthor().asUser().get().getRoles(server);


        List<Role> rolesToRemove = server.getRolesByNameIgnoreCase(roleName);

        if (rolesToRemove.isEmpty()) {

            event.getChannel().sendMessage(":x: 404");

            return;
        }

        Role roleToRemove = rolesToRemove.get(0);

        for (Role role : currentRoles) {

            if (role.getIdAsString().equals(roleToRemove.getIdAsString())) {

                roleToRemove.removeUser(event.getMessageAuthor().asUser().get());

                String message = String.format(":white_check_mark:  no longer subscribed to channel **_%s_**", roleToRemove.getName());

                channel.sendMessage(message);

                return;
            }
        }
    }

    private void listSubscriptions(MessageCreateEvent event) {

        List<String> rssRoles = new ArrayList<>();

        event.getServer().get().getRoles().forEach(role -> {

            if (role.getName().startsWith("rss")) {

                rssRoles.add(role.getName());
            }
        });

        StringBuilder message = new StringBuilder();

        for (String role : rssRoles) {

            message.append("> ").append(role).append("\n");
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor("Zendikar Bot", "https://github.com/erikherbranson/zendikar", "https://raw.githubusercontent.com/erikherbranson/zendikar/master/resources/logo.png")
                .setTitle("List of available RSS channels")
                .addField("Channel names", "```" + message.toString() + "```");

        event.getChannel().sendMessage(embed);
    }

    private void displayOptions(MessageCreateEvent event) {

        EmbedBuilder embed = new EmbedBuilder()
            .setAuthor("Zendikar Bot", "https://github.com/erikherbranson/zendikar", "https://raw.githubusercontent.com/erikherbranson/zendikar/master/resources/logo.png")
            .setTitle("/rss command")
            .addField("Usage", "```" + " /rss --action" + "```")
            .addField(
                "Actions",
                "```" +
                    " --list\n" +
                    "\tlists all available rss channels\n" +
                    " --sub <channelName>\n" +
                    "\tsubscribes you to the provided rss channel name\n" +
                    " --rm <channelName>\n" +
                    "\tun-subscribes you from the provided rss channel name" +
                    "```"
                );

        event.getChannel().sendMessage(embed);
    }

    private String getActionType(String argument) {

        try {

            return argument.trim().split(" ")[0];

        } catch (Exception e) {

            return "";
        }
    }

    private String getRoleName(String argument) {

        try {

            return argument.trim().split(" ")[1];

        } catch (Exception e) {

            return "";
        }
    }

}
