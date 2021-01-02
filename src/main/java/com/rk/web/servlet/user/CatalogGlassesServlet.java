package com.rk.web.servlet.user;

import com.rk.service.GlassesService;

import javax.servlet.http.HttpServlet;

public class CatalogGlassesServlet extends HttpServlet {
    private GlassesService glassesService;
/*
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
    }*/
}
