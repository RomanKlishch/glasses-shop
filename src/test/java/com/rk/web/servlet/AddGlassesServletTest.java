package com.rk.web.servlet;

import com.rk.domain.Glasses;
import com.rk.service.GlassesService;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.web.templator.PageGenerator;
import org.junit.jupiter.api.BeforeEach;
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
class AddGlassesServletTest {

    @Mock
    DefaultGlassesService service;//TODO: во всех классах кроме этого я мокаю интерфейс, я не могу найти разницу в этих классах
    @InjectMocks
    AddGlassesServlet servlet;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    PrintWriter printWriter;
    @Spy
    PageGenerator pageGenerator;

    @Test
    @DisplayName("Test response in method doGet()")
    void doGet() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);
        doNothing().when(pageGenerator).process(any(), any());
        servlet.doGet(request, response);
        pageGenerator.process(anyString(), any());

        verify(response, atLeast(1)).setContentType("text/html;charset=utf-8");
        verify(response, atLeast(1)).getWriter();
        verify(printWriter, atLeast(1)).flush();
        verify(pageGenerator).process(any(), any());
    }

    @Test
    @DisplayName("Check parameters which return from template")
    void doPost() throws IOException {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("collection")).thenReturn("collection");
        when(request.getParameter("category")).thenReturn("SUN");
        when(request.getParameter("details")).thenReturn("details");
        when(request.getParameter("price")).thenReturn("1");
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);

        verify(request, atLeast(1)).getParameter("name");
        verify(request, atLeast(1)).getParameter("collection");
        verify(request, atLeast(1)).getParameter("category");
        verify(request, atLeast(1)).getParameter("details");
        verify(request, atLeast(1)).getParameter("price");
        verify(service).save(any(),any());
    }

}
