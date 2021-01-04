package io.dichotomy.rssbot.repositories;

import io.dichotomy.rssbot.entities.Feed;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedManager {

    @Autowired
    FeedRepository feedRepository;

    public void setFeedRepository(FeedRepository feedRepository) {

        this.feedRepository = feedRepository;
    }

    public FeedRepository getFeedRepository() {

        return feedRepository;
    }

    public void createFeed(String channelId, String url) {

        Feed feed = new Feed();

        feed.set_id(ObjectId.get());
        feed.setChannelId(channelId);
        feed.setUrl(url);
        feed.setLastUpdated(setBackOneMonth(new Date()));

        getFeedRepository().save(feed);
    }

    public List<Feed> readFeeds(String channelId) {

        return getFeedRepository().findByChannelId(channelId);
    }

    public List<Feed> readFeeds() {

        return getFeedRepository().findAll();
    }

    public Feed readFeed(ObjectId objectId) {

        return getFeedRepository().findBy_id(objectId);
    }

    public Feed updateFeed(ObjectId objectId, Feed feed) {

        feed.set_id(objectId);

        getFeedRepository().save(feed);

        return feed;
    }

    public void deleteFeed(ObjectId objectId) {

        getFeedRepository().deleteById(objectId);
    }

    private Long setBackOneMonth(Date date) {

        long ONE_MONTH = (long) 30 * 24 * 60 * 60 * 1000;

        return date.getTime() - ONE_MONTH;
    }
}
