package com.rk.security;

import com.rk.domain.User;
import com.rk.security.entity.Session;

public interface SecurityService {

    String hash(String sole, String uaPassword);

    User login(String login, String uaPassword);
}
