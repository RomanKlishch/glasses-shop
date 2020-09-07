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

public class CategoryServlet extends HttpServlet {
    GlassesService glassesService;

    public CategoryServlet(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        List<Glasses> catalogList;
        String category = "SUN";
        String information;
        if ("sun".equals(category)){
            catalogList = glassesService.findAll();
            information = "SUN";
        }else{
            catalogList = glassesService.findAll();
            information = "OPTICAL";
        }
        pageVariables.put("catalogList", catalogList);
        pageVariables.put("information", information);
        pageVariables.put("category", category);
        response.setContentType("text/html;charset=utf-8");

        PageGenerator.instance().process("catalog", pageVariables, response.getWriter());
    }
}
