package com.rk.web.util;

import com.rk.security.entity.Session;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
public class SessionCleaner implements Runnable {
    private Map<String, Session> sessionMap;

    public SessionCleaner(Map<String, Session> sessionMap) {
        this.sessionMap = sessionMap;
    }

    @Override
    public void run() {
        LocalDateTime time = LocalDateTime.now();
        log.debug("Start clean map of session - {}", time);
        for (String key : sessionMap.keySet()) {
            Session session = sessionMap.get(key);
            if (session.getExpireDate().isAfter(time)) {
                sessionMap.remove(key);
            }
        }
    }
}
