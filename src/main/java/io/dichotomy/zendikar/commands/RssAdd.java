package io.dichotomy.zendikar.commands;

import io.dichotomy.zendikar.repositories.FeedManager;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RssAdd implements MessageCommand {

    @Autowired @Getter @Setter
    FeedManager feedManager;

    @Override
    public void process(MessageCreateEvent event, String argument) {

        TextChannel channel = event.getChannel();

        getFeedManager().createFeed(channel.getIdAsString(), argument);

        // TODO:
        //  - validate that the argument is a valid url
        //  - manual run of feed output

        channel.sendMessage("Feed added");
    }

    @Override
    public Boolean validateUser(User user, Server server) {

        return server.isAdmin(user);

    }
}
