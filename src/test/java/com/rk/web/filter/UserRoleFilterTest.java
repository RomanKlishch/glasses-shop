package com.rk.web.filter;

import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.security.entity.Session;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserRoleFilterTest {
    @InjectMocks
    private UserRoleFilter filter;
    @Mock
    FilterChain filterChain;
    @Mock
    private ServletRequest request;
    @Mock
    private ServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private ServletContext servletContext;
    @Spy
    private Map<String, Session> sessionMap;
    private Cookie cookieAdmin;
    private Session session;


    public UserRoleFilterTest() {
        sessionMap = new HashMap<>();
        session = new Session();
        String token = UUID.randomUUID().toString();
        User userAdmin = User.builder().id(new LongId<>(1L)).email("admin").name("admin").password("admin").role(UserRole.ADMIN).build();
        session.setToken(token);
        session.setUser(userAdmin);
        cookieAdmin = new Cookie("user-token", token);

        sessionMap.put(token, session);

    }


    @Test
    void doFilter() throws IOException, ServletException {
//        Cookie[] cookies = {cookieAdmin};
//        when(request.getCookies()).thenReturn(cookies);
//        when(request.getServletContext()).thenReturn(servletContext);
//        when(servletContext.getAttribute("userTokens")).thenReturn(sessionMap);
//
//        filter.doFilter(request, response, filterChain);
//
//        verify(request).setAttribute("userTokens", session);
//        verify(filterChain).doFilter(request, response);

    }
}
