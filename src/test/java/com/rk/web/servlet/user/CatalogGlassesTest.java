package com.rk.web.servlet.user;

import com.rk.dto.CatalogAndMessage;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogGlassesTest {
    @Mock
    private GlassesService glassesService;
    @InjectMocks
    private CatalogGlassesServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private CatalogAndMessage catalogAndMessage;

    @Test
    @DisplayName("Test method doGet")
    void doGet_Admin() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);
        when(glassesService.getCatalogAndMessage(any())).thenReturn(catalogAndMessage);

        servlet.doGet(request, response);

        verify(glassesService, times(1)).getCatalogAndMessage(null);
        verify(response, times(1)).setContentType("text/html;charset=utf-8");
        verify(response, times(1)).getWriter();
    }
}
