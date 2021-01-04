package io.dichotomy.zendikar.commands;

import io.dichotomy.zendikar.repositories.FeedManager;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RssAdd implements Command {

    @Autowired
    FeedManager feedManager;

    public void setFeedManager(FeedManager feedManager) {

        this.feedManager = feedManager;
    }

    public FeedManager getFeedManager() {

        return feedManager;
    }

    @Override
    public void run(TextChannel channel, String argument) {

        getFeedManager().createFeed(channel.getIdAsString(), argument);

        // TODO:
        //  - validate that the argument is a valid url
        //  - manual run of feed output

        channel.sendMessage("Feed added");
    }
}
