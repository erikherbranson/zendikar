package io.dichotomy.zendikar.commands;

import io.dichotomy.zendikar.repositories.FeedManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RssRemove implements MessageCommand {

    @Autowired
    FeedManager feedManager;

    public void setFeedManager(FeedManager feedManager) {

        this.feedManager = feedManager;
    }

    public FeedManager getFeedManager() {

        return feedManager;
    }

    @Override
    public void process(MessageCreateEvent event, String argument) {

        TextChannel channel = event.getChannel();

        getFeedManager().deleteFeedsWithUrl(channel.getIdAsString(), argument);

        channel.sendMessage("Feed removed");
    }
}
