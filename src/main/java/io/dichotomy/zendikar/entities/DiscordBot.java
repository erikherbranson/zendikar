package io.dichotomy.zendikar.entities;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot {

    private DiscordApi discordApi;
    private BotStatus botStatus = BotStatus.OFFLINE;

    @Value("${discord.api.token}")
    private String token;

    public enum BotStatus {
        RUNNING, OFFLINE
    }

    public void setDiscordApi(DiscordApi discordApi) {

        this.discordApi = discordApi;
    }

    public DiscordApi getDiscordApi() {

        return discordApi;
    }

    public void setBotStatus(BotStatus botStatus) {

        this.botStatus = botStatus;
    }

    public BotStatus getBotStatus() {

        return botStatus;
    }

    private String getToken() {

        return token;
    }

    public void startUp() {
        setDiscordApi(
            new DiscordApiBuilder()
                .setToken(getToken())
                .login()
                .join()
        );

        setBotStatus(BotStatus.RUNNING);
    }

    public void shutdown() {

        getDiscordApi().disconnect();

        setBotStatus(BotStatus.OFFLINE);
    }

}
