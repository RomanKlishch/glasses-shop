package com.rk.web.servlet;

import com.rk.domain.Glasses;
import com.rk.dto.CatalogAndMessage;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogGlassesServlet extends HttpServlet {
    private GlassesService glassesService;

    public CatalogGlassesServlet(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        CatalogAndMessage catalogAndMessage = glassesService.getCatalogAndMessage(request);
        pageVariables.put("catalogList", catalogAndMessage.getCatalog());
        pageVariables.put("information", catalogAndMessage.getMessage());
        response.setContentType("text/html;charset=utf-8");

        PageGenerator.instance().process("catalog", pageVariables, response.getWriter());
    }
}
