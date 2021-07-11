package io.dichotomy.zendikar.controllers;

import io.dichotomy.zendikar.entities.DiscordBot;
import io.dichotomy.zendikar.jobs.UpdateRssFeeds;
import io.dichotomy.zendikar.repositories.FeedManager;
import lombok.Getter;
import lombok.Setter;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ScheduleController {

    @Autowired @Getter @Setter
    private Scheduler scheduler;

    @Autowired @Getter @Setter
    private DiscordBot discordBot;

    @Autowired @Getter @Setter
    private FeedManager feedManager;

    public void dropJobs() {

        try {

            scheduler.clear();

            System.out.println("Cleared all jobs");

        } catch (SchedulerException e) {

            e.printStackTrace();
        }
    }

    public void scheduleJobs() {

        try {

            System.out.println("Starting jobs");

            scheduleRssFeedUpdate();

        } catch (SchedulerException e) {

            e.printStackTrace();
        }
    }

    private void scheduleRssFeedUpdate() throws SchedulerException {

        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("api", getDiscordBot().getDiscordApi());
        jobDataMap.put("repository", getFeedManager());

        JobDetail jobDetail = buildJobDetail(jobDataMap, "rss-feed", UpdateRssFeeds.class);

        Trigger trigger = buildTrigger(jobDetail, "rss-feed", "updating rss feeds");

        scheduler.scheduleJob(jobDetail, trigger);

        System.out.println("UpdateRssFeeds job started");

    }

    private JobDetail buildJobDetail(JobDataMap jobDataMap, String group, Class<? extends Job> className) {

        return JobBuilder
                .newJob(className)
                .withIdentity(UUID.randomUUID().toString(), group)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, String group, String description) {

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), group)
                .withDescription(description)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(20).repeatForever())
                .build();
    }

}
