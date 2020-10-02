package com.rk.web.servlet.admin;

import com.rk.domain.Glasses;
import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.service.GlassesService;
import com.rk.web.servlet.admin.AddGlassesServlet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
class AddGlassesServletTest {
    @Mock
    private GlassesService service;
    @InjectMocks
    private AddGlassesServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    @Test
    @DisplayName("Test method doGet")
    void doGet_Admin() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }

    @Test
    @DisplayName("Check parameters which return from template")
    void doPost() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("collection")).thenReturn("collection");
        when(request.getParameter("category")).thenReturn("SUN");
        when(request.getParameter("details")).thenReturn("details");
        when(request.getParameter("price")).thenReturn("1");
        doNothing().when(service).save(any(Glasses.class));

        servlet.doPost(request, response);

        verify(request, times(1)).getParameter("name");
        verify(request, times(1)).getParameter("collection");
        verify(request, times(1)).getParameter("category");
        verify(request, times(1)).getParameter("details");
        verify(request, times(1)).getParameter("price");
        verify(service, times(1)).save(any(Glasses.class));
    }
}
