package com.rk.web.servlet;

import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.security.SecurityService;
import com.rk.security.entity.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {
    @Mock
    private SecurityService service;
    @InjectMocks
    private LoginServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private ServletContext servletContext;

    @Test
    @DisplayName("Test method doGet")
    void doGet() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response, atLeast(1)).setContentType("text/html;charset=utf-8");
        verify(response, atLeast(1)).getWriter();
    }

    @Test
    @DisplayName("Test method doPost with parameter")
    void doPost_login() throws IOException {
        Session session = new Session();
        User userAdmin = User.builder().id(new LongId<>(1L)).email("admin").name("admin").password("admin").role(UserRole.ADMIN).build();
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(service.login("admin", "admin")).thenReturn(userAdmin);
        when(request.getServletContext()).thenReturn(servletContext);
        when(service.createSession(anyInt())).thenReturn(session);
        when(servletContext.getAttribute("sessionTokens")).thenReturn(anyMap());

        servlet.doPost(request, response);

        verify(service, times(1)).login("admin", "admin");
        verify(request, times(1)).getParameter("login");
        verify(request, times(1)).getParameter("password");
        verify(servletContext, times(1)).getAttribute("sessionTokens");
        verify(response, times(1)).addCookie(any());
        verify(response, times(1)).sendRedirect("");
    }

    @Test
    @DisplayName("Test method doPost when login or password wrong")
    void doPost_NotLogin() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(service.login("admin", "admin")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }
}
