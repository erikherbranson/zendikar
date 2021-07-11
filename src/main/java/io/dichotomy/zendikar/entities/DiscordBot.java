package io.dichotomy.zendikar.entities;

import lombok.Getter;
import lombok.Setter;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot {

    @Getter @Setter
    private DiscordApi discordApi;

    @Getter @Setter
    private BotStatus botStatus = BotStatus.OFFLINE;

    @Value("${discord.api.token}")
    private String token;

    public enum BotStatus {
        RUNNING, OFFLINE
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
