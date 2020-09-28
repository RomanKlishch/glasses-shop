package com.rk.web.servlet;

import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {
    @Mock
    private UserService service;
    @InjectMocks
    private LoginServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private ServletContext context;
    @Spy
    private Map<String, User> cookieUserMap;
    private Cookie cookieAdmin;
    private User userAdmin;

    public LoginServletTest() {
        cookieUserMap = new HashMap<>();
        userAdmin = User.builder().id(new LongId<>(1L)).email("admin").name("admin").password("admin").role(UserRole.ADMIN).build();
        cookieAdmin = new Cookie("user-token", "UUID-admin");
        cookieUserMap.put(cookieAdmin.getValue(), userAdmin);
    }

    @Test
    @DisplayName("Test method doGet")
    void doGet() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response, atLeast(1)).setContentType("text/html;charset=utf-8");
        verify(response, atLeast(1)).getWriter();
    }

    @Test
    @DisplayName("Check parameters which return from template")
    void doPost() throws IOException {
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);

        verify(request, times(1)).getParameter("login");
        verify(request, times(1)).getParameter("password");
    }

    @Test
    @DisplayName("Token creation test ")
    void doPost_CreateToken() throws IOException {
        when(request.getParameter("login")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(service.findByLoginPassword("admin", "admin")).thenReturn(userAdmin);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute("cookieTokens")).thenReturn(cookieUserMap);

        servlet.doPost(request, response);

        verify(cookieUserMap, times(1)).put(anyString(), any());
        verify(response, times(1)).addCookie(any());
        verify(response, times(1)).sendRedirect("");

    }
}
