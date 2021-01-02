package com.rk.web.servlet.user;

import javax.servlet.http.HttpServlet;

public class LogoutServlet extends HttpServlet {
/*    @Override
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
    }*/
}
