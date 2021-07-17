package io.dichotomy.zendikar.jobs;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import io.dichotomy.zendikar.entities.Feed;
import io.dichotomy.zendikar.repositories.FeedManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UpdateRssFeeds implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        System.out.println("UpdateRssFeeds job started");

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

    private SyndFeed getSyndFeed(String url) throws FeedException {

        // some sites only want their feeds used in browsers, this make this server look like a browser
        // avoid 403 responses
        System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:91.0) Gecko/20100101 Firefox/91.0");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        mediaTypes.add(MediaType.APPLICATION_RSS_XML);

        headers.setAccept(mediaTypes);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        SyndFeedInput input = new SyndFeedInput();

        return input.build(new InputSource(new StringReader(res.getBody())));
    }

    private void sendRssContentToChannel(TextChannel channel, Long lastUpdated, SyndFeed syndFeed) {

        List<SyndEntry> entries = syndFeed.getEntries();

        for (int i = entries.size() - 1; i >= 0; i--) {

            SyndEntry syndEntry = entries.get(i);

            long feedTimestamp = 0L;

            if (syndEntry.getPublishedDate() != null) {
                feedTimestamp = syndEntry.getPublishedDate().getTime();
            }

            if (syndEntry.getUpdatedDate() != null) {
                feedTimestamp = syndEntry.getUpdatedDate().getTime();
            }

            if (lastUpdated < feedTimestamp) {

                channel.sendMessage(syndEntry.getLink().startsWith("http") ? syndEntry.getLink() : "https:" + syndEntry.getLink());
            }
        }

    }

}
