package ru.kharkov.eventservice.scheduling;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.kharkov.eventservice.event.EventService;

@Component
public class UpdateEventsStatusScheduler {

    @Autowired
    private EventService eventService;

    private static final Logger logger = LogManager.getLogger(UpdateEventsStatusScheduler.class);


    @Scheduled(cron = "${scheduling.events.status-updates}")
    public void updatesEventsStatus() {
        logger.info("Event Scheduler called");
        eventService.updateEventsStatus();
        logger.info("Event Scheduler finished");
    }
}
