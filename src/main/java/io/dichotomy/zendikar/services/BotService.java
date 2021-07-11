package io.dichotomy.zendikar.services;

import io.dichotomy.zendikar.controllers.DiscordBotController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BotService {


    @Autowired @Getter @Setter
    DiscordBotController discordBotController;

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
