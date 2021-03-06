package io.dichotomy.zendikar.services;

import io.dichotomy.zendikar.entities.Feed;
import io.dichotomy.zendikar.repositories.FeedManager;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FeedService {

    @Autowired
    @Getter @Setter
    FeedManager feedManager;

    @GetMapping("/feeds")
    @ResponseBody
    public List<Feed> getFeeds() {

        return getFeedManager().readFeeds();
    }

    @GetMapping("/feeds/{feedId}")
    @ResponseBody
    public Feed getFeed(@PathVariable("feedId") ObjectId feedId) {

        return getFeedManager().readFeed(feedId);
    }

}
