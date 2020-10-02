package com.rk.web.filter;

import com.rk.security.entity.Session;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UserRoleFilter implements Filter {

    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        httpRequest = (HttpServletRequest) request;
        httpResponse = (HttpServletResponse) response;
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    Map<String, Session> userTokens = (Map<String, Session>) request.getServletContext().getAttribute("userTokens");
                    Session session = userTokens.get(cookie.getValue());
                    if (session != null) {
                        request.setAttribute("session", session);
                        chain.doFilter(httpRequest, httpResponse);
                        return;
                    }
                }
            }

            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/login");
            request.setAttribute("message", "You must be logged in to access this resource");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            dispatcher.forward(httpRequest, httpResponse);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
