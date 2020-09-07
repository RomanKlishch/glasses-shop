package com.rk.web.servlet;

import com.rk.domain.Glasses;
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
    GlassesService glassesService;

    public CatalogGlassesServlet(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        List<Glasses> catalogList;
        String information;

        String name = request.getParameter("searchName");
        if (name!=null){
            catalogList = glassesService.findByName(name);
            information = "Found by your request";
        }else {
            catalogList = glassesService.findAll();
            information = "Catalog of glasses";
        }
        pageVariables.put("catalogList", catalogList);
        pageVariables.put("information", information);
        response.setContentType("text/html;charset=utf-8");

        PageGenerator.instance().process("catalog", pageVariables, response.getWriter());
    }
}
