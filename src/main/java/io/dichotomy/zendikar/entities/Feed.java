package io.dichotomy.zendikar.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Feed {

    @Id
    private ObjectId _id;
    private String channelId;
    private String url;
    private Long lastUpdated;

    public Feed() {

    }

    public Feed(ObjectId _id, String channelId, String url, Long lastUpdated) {
        this._id = _id;
        this.channelId = channelId;
        this.url = url;
        this.lastUpdated = lastUpdated;
    }

    public ObjectId get_id() {

        return _id;
    }

    public void set_id(ObjectId _id) {

        this._id = _id;
    }

    public String getChannelId() {

        return channelId;
    }

    public void setChannelId(String channelId) {

        this.channelId = channelId;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public Long getLastUpdated() {

        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {

        this.lastUpdated = lastUpdated;
    }
}
