package com.rk.web.servlet;

import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ArticleServletTest {
    @InjectMocks
    private ArticleServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Spy
    Map<String, User> cookieUserMap;
    private Cookie cookieAdmin;
    private Cookie cookieUser;

    public ArticleServletTest() {
        cookieUserMap = new HashMap<>();

        User userAdmin = User.builder().id(new LongId<>(1L)).email("admin").name("admin").password("admin").role(UserRole.ADMIN).build();
        User userUser = User.builder().id(new LongId<>(1L)).email("user").name("user").password("user").role(UserRole.USER).build();

        cookieAdmin = new Cookie("user-token","admin");
        cookieUser = new Cookie("user-token","user");
        cookieUserMap.put(cookieAdmin.getValue(),userAdmin);
        cookieUserMap.put(cookieUser.getValue(),userUser);
    }

    @Test
    @DisplayName("Test method doGet when user role is admin ")
    void doGet_Admin() throws IOException {
        Cookie [] cookies = {cookieUser};
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getCookies()).thenReturn(cookies);

        servlet.doGet(request, response);

        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }

    @Test
    @DisplayName("Test method doGet when user role is user ")
    void doGet_User() throws IOException {
        Cookie [] cookies = {cookieUser};
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getCookies()).thenReturn(cookies);

        servlet.doGet(request, response);

        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }

    @Test
    @DisplayName("Test redirect in method doGet() when user role is guest")
    void doGet_Redirect() throws IOException {
        Cookie [] cookies = new Cookie[0];
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getCookies()).thenReturn(cookies);

        servlet.doGet(request, response);

        verify(response, times(1)).sendRedirect("/login");
    }
}