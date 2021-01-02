package com.rk.web.filter;

import com.rk.domain.UserRole;
import com.rk.security.entity.Session;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("AdminRoleFilter")
public class AdminRoleFilter implements Filter {

    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        httpRequest = (HttpServletRequest) request;
        httpResponse = (HttpServletResponse) response;

        Session session = (Session) request.getAttribute("session");

        if (session.getUser().getRole().equals(UserRole.ADMIN)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/login");
            httpRequest.setAttribute("message", "You must be logged in as ADMIN to access this resource");
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
