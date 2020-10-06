package com.rk.web.util;

import com.rk.security.entity.Session;

import java.util.Map;

public class SessionCleaner implements Runnable {
    private Map<String, Session> sessionMap;

    public SessionCleaner(Map<String, Session> sessionMap) {
        this.sessionMap = sessionMap;
    }

    @Override
    public void run() {
       for (String key : sessionMap.keySet()) {
            Session session = sessionMap.get(key);
            if (session.getExpireDate().isAfter(session.getExpireDate())) {
                sessionMap.remove(key);
            }
        }
    }
}
