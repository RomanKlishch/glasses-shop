package com.rk.web.filter;

import com.rk.security.entity.Session;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class UserRoleFilter implements Filter {
    private static final Map<String, Session> sessionTokens = new ConcurrentHashMap<>();
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
                    Session session = sessionTokens.get(cookie.getValue());
                    if (session != null) {
                        if (session.getExpireDate().isAfter(LocalDateTime.now())) {
                            httpRequest.setAttribute("session", session);
                            chain.doFilter(httpRequest, httpResponse);
                            return;
                        } else {
                            sessionTokens.remove(cookie.getValue());
                            break;
                        }
                    }
                }
            }
            httpRequest.setAttribute("message", "You must be logged in to access this resource");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.sendRedirect("/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        servletContext.setAttribute("sessionTokens", sessionTokens);
    }

    @Override
    public void destroy() {
    }
}
