package com.rk.web.controller;

import com.rk.domain.User;
import com.rk.security.SecurityService;
import com.rk.security.entity.Session;
import com.rk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@PropertySource("classpath:properties/application.properties")
public class UserController {

    @Autowired
    @Qualifier("sessionTokens")
    Map<String, Session> sessionTokens;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @Value("${life.time}")
    private int lifeTime;

    @GetMapping(path = "/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response, HttpServletRequest request) throws IOException {
        User user = securityService.login(login, password);
        if (user != null) {
            Cookie cookie = registrationSessionAndCookie(user,request);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "login";
        }
    }

    @GetMapping(path = "/registration")
    public String getRegistration() {
      return "registration";
    }

    @PostMapping(path = "/registration")
    public String login(@RequestParam String name, @RequestParam String email, @RequestParam String password,HttpServletResponse response) throws IOException {
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) {
        Map<String, Session> sessionTokens = (Map<String, Session>) request.getServletContext().getAttribute("sessionTokens");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user-token")) {
                sessionTokens.remove(cookie.getValue());
                cookie.setMaxAge(0);
                break;
            }
        }
        return "redirect:/login";
    }

    private Cookie registrationSessionAndCookie(User user,HttpServletRequest request) {
        Map<String, Session> sessionTokens = (Map<String, Session>) request.getServletContext().getAttribute("sessionTokens");
        Session session = securityService.createSession(lifeTime);
        session.setUser(user);
        sessionTokens.put(session.getToken(), session);
        Cookie cookie = new Cookie("user-token", session.getToken());
        cookie.setMaxAge(lifeTime);
        return cookie;
    }
}
