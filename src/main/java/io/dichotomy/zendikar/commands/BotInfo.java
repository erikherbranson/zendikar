package io.dichotomy.zendikar.commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class BotInfo implements MessageCommand {

    @Override
    public void process(MessageCreateEvent event, String argument) {

        EmbedBuilder embed = new EmbedBuilder()
            .setAuthor("Erik Herbranson", "https://github.com/erikherbranson", "https://github.com/erikherbranson.png")
            .setTitle("Zendikar Bot Information")
            .addField(
                "Commands",
                "```" +
                    "@everybody\n> /zendikar\n> /ping\n> /rss\n\n@admin\n> /rss-add\n> /rss-remove" +
                    "```"
            )
            .addField("Repository", "https://github.com/erikherbranson/zendikar")
            .addField("Report a bug", "https://github.com/erikherbranson/zendikar/issues")
            .setImage("https://repository-images.githubusercontent.com/326538337/57976c80-e1af-11eb-88f9-3f257c98dc2f");

        event.getChannel().sendMessage(embed);
    }

    @Override
    public Boolean validateUser(User user, Server server) {

        return true;
    }
}
