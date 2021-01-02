package com.rk.web.filter;

import com.rk.security.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component("UserRoleFilter")
public class UserRoleFilter implements Filter {

    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;

    @Autowired
    @Qualifier("sessionTokens")
    Map<String, Session> sessionTokens;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        httpRequest = (HttpServletRequest) request;
        httpResponse = (HttpServletResponse) response;
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("user-token")) {
                    Session session = sessionTokens.get(cookie.getValue());
                    if (session != null) {
                        if (session.getExpireDate().isAfter(LocalDateTime.now())) {
                            request.setAttribute("session", session);
                            chain.doFilter(httpRequest, httpResponse);
                            return;
                        } else {
                            sessionTokens.remove(cookie.getValue());
                            break;
                        }
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
