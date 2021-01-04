package io.dichotomy.zendikar.controllers;

import io.dichotomy.zendikar.commands.*;
import org.javacord.api.event.message.MessageCreateEvent;
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

    @Autowired
    private RoleAdd roleAdd;

    @Autowired
    private RoleRemove roleRemove;

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

    public void setRoleAdd(RoleAdd roleAdd) {

        this.roleAdd = roleAdd;
    }

    public RoleAdd getRoleAdd() {

        return roleAdd;
    }

    public void setRoleRemove(RoleRemove roleRemove) {

        this.roleRemove = roleRemove;
    }

    public RoleRemove getRoleRemove() {

        return roleRemove;
    }

    public void initiateChannelCommand(MessageCreateEvent event, String messageContent) {

        Command command = getChannelCommand(getChannelCommandType(messageContent));

        command.run(event, getChannelCommandArgument(messageContent));

    }

    private Command getChannelCommand(String command) {

        switch (command) {

            case "!ping":

                return getPing();

            case "!rss-add":

                return getRssAdd();

            case "!rss-remove":

                return getRssRemove();

            case "!role-add":

                return getRoleAdd();

            case "!role-remove":

                return getRoleRemove();

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
