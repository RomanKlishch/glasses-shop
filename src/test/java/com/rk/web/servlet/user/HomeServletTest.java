package com.rk.web.servlet.user;

import com.rk.dto.FeaturesAndSpecialGlasses;
import com.rk.service.GlassesService;
import org.junit.jupiter.api.DisplayName;
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
class HomeServletTest {
    @Mock
    private GlassesService glassesService;
    @InjectMocks
    private HomeServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private FeaturesAndSpecialGlasses featuresAndSpecialGlasses;


    @Test
    @DisplayName("Test method doGet")
    void doGet_Admin() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);
        when(glassesService.getListsFeaturesAndSpecialGlasses(anyInt(), anyInt())).thenReturn(featuresAndSpecialGlasses);

        servlet.doGet(request, response);

        verify(glassesService, times(1)).getListsFeaturesAndSpecialGlasses(anyInt(), anyInt());
        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }
}