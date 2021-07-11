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
    private RssAdd rssAdd;

    @Autowired @Getter @Setter
    private RssRemove rssRemove;

    @Autowired @Getter @Setter
    private Rss rss;

    @Autowired @Getter @Setter
    private BotInfo botInfo;

    public void initiateChannelCommand(MessageCreateEvent event, String messageContent) {

        MessageCommand messageCommand = getChannelCommand(getChannelCommandType(messageContent));

        if (messageCommand == null) {
            //quit, not a recognized command
            return;
        }

        if (messageCommand.validateUser(event.getMessageAuthor().asUser().get(), event.getServer().get())) {

            messageCommand.process(event, getChannelCommandArgument(messageContent));

        } else {

            event.getChannel().sendMessage("Not authorized");
            event.getChannel().sendMessage("http://gph.is/1Vo35Zc");
        }
    }

    private MessageCommand getChannelCommand(String command) {

        switch (command) {

            case "/zendikar":
                return getBotInfo();

            case "/ping":
                return getPing();

            case "/rss":
                return getRss();

            case "/rss-add":
                return getRssAdd();

            case "/rss-remove":
                return getRssRemove();

            default:
                return null;
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

            String[] temp = messageContent.trim().split(" ");
            StringBuilder sb = new StringBuilder();

            int length = temp.length;
            int lastIndex = length - 1;

            for (int i = 0; i < length; i++) {

                if (i == 0) {
                    continue;
                }

                sb.append(temp[i]);

                 if (i != lastIndex) {
                     sb.append(" ");
                 }
            }

            return sb.toString();

        } catch (Exception e) {

            return "";
        }
    }

}
