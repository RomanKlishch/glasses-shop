package com.rk.web.servlet;

import com.rk.dto.CatalogAndMessage;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CatalogGlassesTest {
    @Mock
    GlassesService glassesService;
    @InjectMocks
    CatalogGlassesServlet servlet;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    PrintWriter printWriter;
    @Mock
    CatalogAndMessage catalogAndMessage;
    @Spy
    PageGenerator pageGenerator;

    @Test
    @DisplayName("Test response in method doGet()")
    void doGet() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);
        when(glassesService.getCatalogAndMessage(any())).thenReturn(catalogAndMessage);
        doNothing().when(pageGenerator).process(any(), any(), any());
        servlet.doGet(request, response);
        pageGenerator.process(anyString(), any(), any());

        verify(glassesService, atLeast(1)).getCatalogAndMessage(request);
        verify(response, atLeast(1)).setContentType("text/html;charset=utf-8");
        verify(response, atLeast(1)).getWriter();
        verify(printWriter, atLeast(1)).flush();
        verify(pageGenerator).process(any(), any(), any());
    }

}
