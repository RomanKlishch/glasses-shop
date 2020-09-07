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

public class HomeServlet extends HttpServlet {
    GlassesService glassesService;

    public HomeServlet(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        List<Glasses> glassesList = glassesService.findRandomGlasses(9);
        List<Glasses> specialList = glassesService.findRandomGlasses(6);
        pageVariables.put("randomList", glassesList);
        pageVariables.put("specialList", specialList);
        response.setContentType("text/html;charset=utf-8");

        PageGenerator.instance().process("index", pageVariables, response.getWriter());
    }
}
