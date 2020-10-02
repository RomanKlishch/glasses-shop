package com.rk.web.servlet.user;

import com.rk.domain.Glasses;
import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.security.entity.Session;
import com.rk.service.GlassesService;
import com.rk.web.servlet.user.GlassesServlet;
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
class GlassesServletTest {
    @Mock
    private GlassesService glassesService;
    @InjectMocks
    private GlassesServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    Session session;

    @Test
    @DisplayName("Test method doGet")
    void doGet_Admin() throws IOException {
        User userAdmin = User.builder().id(new LongId<>(1L)).email("admin").name("admin").password("admin").role(UserRole.ADMIN).build();
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getPathInfo()).thenReturn("sun/1");
        when(glassesService.findById(anyLong())).thenReturn(new Glasses());
        when(request.getAttribute("session")).thenReturn(session);
        when(session.getUser()).thenReturn(userAdmin);

        servlet.doGet(request, response);

        verify(glassesService).findById(anyLong());
        verify(request).getPathInfo();
        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }
}