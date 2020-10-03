package com.rk.security;

import com.rk.domain.User;

public interface SecurityService {

    String hash(String sole, String password);

    User login(String login, String uaPassword);
}
