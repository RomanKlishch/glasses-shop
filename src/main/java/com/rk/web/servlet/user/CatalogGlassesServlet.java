package com.rk.web.servlet.user;

import com.rk.ServiceLocator;
import com.rk.dto.CatalogAndMessage;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.rk.constants.WebConstants.CONTENT_TYPE;

public class CatalogGlassesServlet extends HttpServlet {
    private GlassesService glassesService;

    public CatalogGlassesServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = new HashMap<>();

        String nameOfCatalog = request.getParameter("searchName");
        CatalogAndMessage catalogAndMessage = glassesService.getCatalogAndMessage(nameOfCatalog);
        pageVariables.put("catalogList", catalogAndMessage.getCatalog());
        pageVariables.put("information", catalogAndMessage.getMessage());

        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("catalog", pageVariables, response.getWriter());
    }
}
