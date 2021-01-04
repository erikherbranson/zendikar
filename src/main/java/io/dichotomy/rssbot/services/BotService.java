package io.dichotomy.rssbot.services;

import io.dichotomy.rssbot.controllers.DiscordBotController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BotService {


    @Autowired
    DiscordBotController discordBotController;

    public void setDiscordBotController(DiscordBotController discordBotController) {

        this.discordBotController = discordBotController;
    }

    public DiscordBotController getDiscordBotController() {

        return discordBotController;
    }

    @GetMapping("/start")
    @ResponseBody
    public String startBot() {

        getDiscordBotController().initializeBot();

        return "started";
    }

    @GetMapping("/stop")
    @ResponseBody
    public String stopBot() {

        getDiscordBotController().shutdownBot();

        return "stopped";
    }

    @GetMapping("/test")
    @ResponseBody
    public String sayHello() {

        return "Hello world!";
    }

}
