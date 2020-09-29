package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.User;
import com.rk.service.GlassesService;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class DeleteServlet extends HttpServlet {
    private GlassesService glassesService;
    private Map<String, User> cookieTokens;

    public DeleteServlet() {
        this.glassesService = ServiceLocator.getBean(GlassesService.class);
        this.cookieTokens = ServiceLocator.getBean(Map.class);
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    User user = cookieTokens.get(cookie.getValue());
                    if (user != null && user.getRole().getUserRole().equals("ADMIN")) {
                        long id = Long.parseLong(request.getParameter("id"));
                        glassesService.deleteById(id);
                        dispatcher = request.getRequestDispatcher("/home");
                        dispatcher.forward(request, response);
                        return;
                    }
                }
            }
            dispatcher = request.getRequestDispatcher("/login");
            request.setAttribute("message", "You must be logged in to access this resource");
            dispatcher.forward(request, response);
        }
    }
}
