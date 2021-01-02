package com.rk.web.util;

import com.rk.security.entity.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulerSession {

    @Autowired()
    Map<String, Session> sessionTokensMap;

    @Async
    @Scheduled(cron = "0 1 * * * *")
    public void run() {
        LocalDateTime time = LocalDateTime.now();
        log.debug("Start clean map of session - {}", time);
        for (String key : sessionTokensMap.keySet()) {
            Session session = sessionTokensMap.get(key);
            if (session.getExpireDate().isAfter(time)) {
                sessionTokensMap.remove(key);
            }
        }
    }

}
