package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.User;
import com.rk.service.GlassesService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DeleteServlet extends HttpServlet {
    private GlassesService glassesService;

    public DeleteServlet() {
        this.glassesService = ServiceLocator.getBean(GlassesService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    Map<String, User> cookieTokens = (Map<String, User>) request.getServletContext().getAttribute("cookieTokens");
                    User user = cookieTokens.get(cookie.getValue());
                    if (user != null && user.getRole().getUserRole().equals("ADMIN")) {
                        long id = Long.parseLong(request.getParameter("id"));
                        glassesService.deleteById(id);
                        response.sendRedirect("");
                        return;
                    }
                }
            }
            response.sendRedirect("/login");
        }
    }
}
