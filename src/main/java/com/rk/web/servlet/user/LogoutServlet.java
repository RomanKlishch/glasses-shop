package com.rk.web.servlet.user;

import com.rk.security.entity.Session;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user-token")) {
                Map<String, Session> sessionTokens = (Map<String, Session>) request.getServletContext().getAttribute("sessionTokens");
                sessionTokens.remove(cookie.getValue());
                cookie.setMaxAge(0);
                break;
            }
        }
        response.sendRedirect("/login");
    }
}
