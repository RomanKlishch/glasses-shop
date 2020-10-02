package com.rk.web.servlet.admin;

import com.rk.domain.Glasses;
import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.service.GlassesService;
import com.rk.web.servlet.admin.EditGlassesServlet;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditGlassesServletTest {
    @Mock
    private GlassesService service;
    @InjectMocks
    private EditGlassesServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    @Test
    @DisplayName("Test method doGet")
    void doGet_Admin() throws IOException {
        Glasses glasses = new Glasses();
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("id")).thenReturn("1");
        when(service.findById(1L)).thenReturn(glasses);

        servlet.doGet(request, response);

        verify(service, times(1)).findById(1);
        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
        verify(service,times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check parameters which return from template")
    void doPost() throws IOException, ServletException {
        when(request.getParameter("glassesId")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("collection")).thenReturn("collection");
        when(request.getParameter("category")).thenReturn("SUN");
        when(request.getParameter("details")).thenReturn("details");
        when(request.getParameter("price")).thenReturn("1");
        doNothing().when(service).update(any(Glasses.class));

        servlet.doPost(request, response);

        verify(request, times(1)).getParameter("glassesId");
        verify(request, times(1)).getParameter("name");
        verify(request, times(1)).getParameter("collection");
        verify(request, times(1)).getParameter("category");
        verify(request, times(1)).getParameter("details");
        verify(request, times(1)).getParameter("price");
        verify(service, times(1)).update(any(Glasses.class));
    }

}