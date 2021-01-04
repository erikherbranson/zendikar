package io.dichotomy.zendikar.controllers;

import io.dichotomy.zendikar.entities.DiscordBot;
import org.javacord.api.DiscordApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscordBotController {

    private final String prefix = "!";

    @Autowired
    DiscordBot discordBot;

    @Autowired
    CommandController commandController;

    @Autowired
    ScheduleController scheduleController;

    public void setDiscordBot(DiscordBot discordBot) {

        this.discordBot = discordBot;
    }

    public DiscordBot getDiscordBot() {

        return discordBot;
    }

    public void setCommandController(CommandController commandController) {

        this.commandController = commandController;
    }

    public CommandController getCommandController() {

        return commandController;
    }

    public void setScheduleController(ScheduleController scheduleController) {

        this.scheduleController = scheduleController;
    }

    public ScheduleController getScheduleController() {

        return scheduleController;
    }

    public void initializeBot() {

      getDiscordBot().startUp();

      registerAllListeners(getDiscordBot().getDiscordApi());

      getScheduleController().scheduleJobs();
    }

    public void shutdownBot() {

        getDiscordBot().shutdown();

        getScheduleController().dropJobs();
    }

    private void registerAllListeners(DiscordApi discordApi) {

        registerMessageListener(discordApi);

        registerNewMemberListener(discordApi);
    }

    private void registerMessageListener(DiscordApi discordApi) {

        discordApi.addMessageCreateListener(event -> {

            event.getMessageAuthor().asUser();

            if (event.getMessageContent().startsWith(prefix)) {

                getCommandController().initiateChannelCommand(event, event.getMessageContent());
            }

        });
    }

    private void registerNewMemberListener(DiscordApi discordApi) {

        discordApi.addServerMemberJoinListener(event -> {

            getCommandController().initiateServerCommand("MEMBER_JOINED");

        });
    }

}
