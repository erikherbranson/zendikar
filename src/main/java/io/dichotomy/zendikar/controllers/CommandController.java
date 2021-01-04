package io.dichotomy.zendikar.controllers;

import io.dichotomy.zendikar.commands.*;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandController {

    @Autowired
    private Ping ping;

    @Autowired
    private Unknown unknown;

    @Autowired
    private RssAdd rssAdd;

    @Autowired
    private RssRemove rssRemove;

    public Ping getPing() {

        return ping;
    }

    public void setPing(Ping ping) {

        this.ping = ping;
    }

    public Unknown getUnknown() {

        return unknown;
    }

    public void setUnknown(Unknown unknown) {

        this.unknown = unknown;
    }

    public RssAdd getRssAdd() {

        return rssAdd;
    }

    public void setRssAdd(RssAdd rssAdd) {

        this.rssAdd = rssAdd;
    }

    public void setRssRemove(RssRemove rssRemove) {

        this.rssRemove = rssRemove;
    }

    public RssRemove getRssRemove() {

        return rssRemove;
    }

    public void initiateChannelCommand(TextChannel channel, String messageContent) {

        Command command = getChannelCommand(getChannelCommandType(messageContent));

        command.run(channel, getChannelCommandArgument(messageContent));

    }

    private Command getChannelCommand(String command) {

        switch (command) {

            case "!ping":

                return getPing();

            case "!rss-add":

                return getRssAdd();

            case "!rss-remove":

                return getRssRemove();

            default:

                return getUnknown();
        }
    }

    public void initiateServerCommand(String commandType) {

    }

    private String getChannelCommandType(String messageContent) {

        try {

            return messageContent.trim().split(" ")[0];

        } catch (Exception e) {

            return "";
        }
    }

    private String getChannelCommandArgument(String messageContent) {

        try {

            return messageContent.trim().split(" ")[1];

        } catch (Exception e) {

            return "";
        }
    }

}