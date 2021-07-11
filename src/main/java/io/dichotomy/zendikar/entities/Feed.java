package io.dichotomy.zendikar.entities;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Feed {

    @Id @Getter @Setter
    private ObjectId _id;

    @Getter @Setter
    private String channelId;

    @Getter @Setter
    private String url;

    @Getter @Setter
    private Long lastUpdated;

    public Feed() {

    }

    public Feed(ObjectId _id, String channelId, String url, Long lastUpdated) {
        this._id = _id;
        this.channelId = channelId;
        this.url = url;
        this.lastUpdated = lastUpdated;
    }
}
