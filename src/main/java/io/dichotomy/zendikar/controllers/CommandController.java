package io.dichotomy.zendikar.controllers;

import io.dichotomy.zendikar.commands.*;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandController {

    @Autowired @Getter @Setter
    private Ping ping;

    @Autowired @Getter @Setter
    private Unknown unknown;

    @Autowired @Getter @Setter
    private RssAdd rssAdd;

    @Autowired @Getter @Setter
    private RssRemove rssRemove;

    @Autowired @Getter @Setter
    private RoleAdd roleAdd;

    @Autowired @Getter @Setter
    private RoleRemove roleRemove;


    public void initiateChannelCommand(MessageCreateEvent event, String messageContent) {

        MessageCommand messageCommand = getChannelCommand(getChannelCommandType(messageContent));

        messageCommand.process(event, getChannelCommandArgument(messageContent));

    }

    private MessageCommand getChannelCommand(String command) {

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
