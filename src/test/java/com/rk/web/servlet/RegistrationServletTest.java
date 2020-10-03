package com.rk.web.servlet;

import com.rk.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServletTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private RegistrationServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    @Test
    void doGet() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();

    }

    @Test
    void doPost() throws IOException {
        when(request.getParameter("name")).thenReturn("admin");
        when(request.getParameter("email")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");

        servlet.doPost(request, response);

        verify(request, times(1)).getParameter("name");
        verify(request, times(1)).getParameter("email");
        verify(request, times(1)).getParameter("password");
        verify(userService, times(1)).save(any());
        verify(response, times(1)).sendRedirect("/login");
    }
}