package com.rk.web.servlet;

import com.rk.domain.Glasses;
import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.service.GlassesService;
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
    private RequestDispatcher dispatcher;
    @Spy
    private Map<String, User> cookieUserMap;
    private Cookie cookieAdmin;
    private Cookie cookieUser;

    public GlassesServletTest() {
        cookieUserMap = new HashMap<>();

        User userAdmin = User.builder().id(new LongId<>(1L)).email("admin").name("admin").password("admin").role(UserRole.ADMIN).build();
        User userUser = User.builder().id(new LongId<>(1L)).email("user").name("user").password("user").role(UserRole.USER).build();

        cookieAdmin = new Cookie("user-token", "admin");
        cookieUser = new Cookie("user-token", "user");
        cookieUserMap.put(cookieAdmin.getValue(), userAdmin);
        cookieUserMap.put(cookieUser.getValue(), userUser);
    }

    @Test
    @DisplayName("Test method doGet when user role is admin ")
    void doGet_Admin() throws IOException {
        Cookie[] cookies = {cookieUser};
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getCookies()).thenReturn(cookies);
        when(request.getPathInfo()).thenReturn("sun/1");
        when(glassesService.findById(anyLong())).thenReturn(new Glasses());

        servlet.doGet(request, response);

        verify(glassesService).findById(anyLong());
        verify(request).getPathInfo();
        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }

    @Test
    @DisplayName("Test method doGet when user role is user ")
    void doGet_User() throws IOException {
        Cookie[] cookies = {cookieUser};
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getCookies()).thenReturn(cookies);
        when(request.getPathInfo()).thenReturn("sun/1");
        when(glassesService.findById(anyLong())).thenReturn(new Glasses());

        servlet.doGet(request, response);

        verify(glassesService).findById(anyLong());
        verify(request).getPathInfo();
        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }

    @Test
    @DisplayName("Test redirect in method doGet() when user role is guest")
    void doGet_Redirect() throws IOException, ServletException {
        Cookie[] cookies = new Cookie[0];
        when(request.getCookies()).thenReturn(cookies);
        when(request.getRequestDispatcher("/login")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher, times(1)).forward(request, response);
    }
}