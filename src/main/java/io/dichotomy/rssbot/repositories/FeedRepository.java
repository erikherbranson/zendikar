package io.dichotomy.rssbot.repositories;

import io.dichotomy.rssbot.entities.Feed;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedRepository extends MongoRepository<Feed, ObjectId> {

    Feed findBy_id(ObjectId _id);

    List<Feed> findByChannelId(String channelId);

    List<Feed> findByUrl(String url);
}
