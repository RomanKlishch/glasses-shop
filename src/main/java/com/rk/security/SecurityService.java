package com.rk.security;

import com.rk.domain.User;
import com.rk.security.entity.Session;

public interface SecurityService {

    String hash(String sole, String password);

    User login(String login, String uaPassword);

    Session createSession(int lifeTime);
}
