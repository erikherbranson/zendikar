package io.dichotomy.zendikar.jobs;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import io.dichotomy.zendikar.entities.Feed;
import io.dichotomy.zendikar.repositories.FeedManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UpdateRssFeeds implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        DiscordApi discordApi = (DiscordApi) jobDataMap.get("api");
        FeedManager feedManager = (FeedManager) jobDataMap.get("repository");

        List<Feed> feeds = feedManager.readFeeds();

        for (Feed feed : feeds) {

            Optional<TextChannel> channel = discordApi.getTextChannelById(feed.getChannelId());

           if (channel.isPresent()) {

               try {

                SyndFeed syndFeed = getSyndFeed(feed.getUrl());

                sendRssContentToChannel(channel.get(), feed.getLastUpdated(), syndFeed);

                feed.setLastUpdated(new Date().getTime());
                feedManager.updateFeed(feed.get_id(), feed);

               } catch (Exception e) {

                   e.printStackTrace();

               }

           } else {

               feedManager.deleteFeed(feed.get_id());

           }
        }
    }

    private SyndFeed getSyndFeed(String url) throws IOException, FeedException {

        InputStream response = new URL(url).openStream();

        SyndFeedInput input = new SyndFeedInput();

        return input.build(new InputStreamReader(response));
    }

    private void sendRssContentToChannel(TextChannel channel, Long lastUpdated, SyndFeed syndFeed) {

        syndFeed.getEntries().forEach(syndEntry -> {
            if (lastUpdated < syndEntry.getPublishedDate().getTime()) {

                buildMessage(syndEntry).send(channel);
            }
        });
    }

    private MessageBuilder buildMessage(SyndEntry syndEntry) {

        return new MessageBuilder()
            .setEmbed(
                new EmbedBuilder()
                    .setAuthor(syndEntry.getAuthor())
                    .setTitle(syndEntry.getTitle())
                    .setDescription(syndEntry.getLink().startsWith("https:") ? syndEntry.getLink() : "https:" + syndEntry.getLink())
            );
    }

}
