package com.example.monolithic.schedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Schedule {

    private static final Logger logger = LoggerFactory.getLogger(Schedule.class);
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss:SS");

    @Scheduled(fixedDelay = 5000)
    public void printTime(){
        logger.info("Time right now is = "+ time.format(new Date()));
    }
}
