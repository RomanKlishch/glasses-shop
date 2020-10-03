package com.rk.web.servlet.user;

import com.rk.security.entity.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {
    @InjectMocks
    private LogoutServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext servletContext;
    private Map<String, Session> sessionTokens = new ConcurrentHashMap<>();
    private Cookie cookieAdmin;
    private Session session;

    public LogoutServletTest() {
        String token = UUID.randomUUID().toString();
        session = Session.builder().token(token).build();
        cookieAdmin = new Cookie("user-token", token);
        sessionTokens.put(token, session);
    }

    @Test
    @DisplayName("Test method doGet")
    void doGet() throws IOException, ServletException {
        Cookie[] cookies = {cookieAdmin};
        when(request.getCookies()).thenReturn(cookies);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("sessionTokens")).thenReturn(sessionTokens);

        servlet.doGet(request, response);

        verify(response, times(1)).sendRedirect("/login");
        assertEquals(0, cookieAdmin.getMaxAge());
        assertEquals(0, sessionTokens.size());
    }
}