package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.User;
import com.rk.service.UserService;
import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private UserService userService;
    private Map<String, User> cookieTokens;

    public LoginServlet() {
        this.userService = ServiceLocator.getBean(UserService.class);
        this.cookieTokens = ServiceLocator.getBean(Map.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", request.getAttribute("message"));
        response.setContentType("text/html;charset=utf-8");
        PageGenerator.instance().process("login", pageVariables, response.getWriter());
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.findByLoginPassword(login, password);
        if (user != null) {
            String cookieToken = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user-token", cookieToken);
            cookieTokens.put(cookieToken, user);
            response.addCookie(cookie);
            String path = request.getHeader("Referer");

//            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//            dispatcher.forward(request,response);

            response.sendRedirect(path);

        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("message", "Login or password not found");

            response.setContentType("text/html;charset=utf-8");
            PageGenerator.instance().process("login", pageVariables, response.getWriter());
        }
    }
}
