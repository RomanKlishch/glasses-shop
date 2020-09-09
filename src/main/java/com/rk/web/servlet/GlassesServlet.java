package com.rk.web.servlet;

import com.rk.domain.Glasses;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GlassesServlet extends HttpServlet {
    private GlassesService glassesService;

    public GlassesServlet(GlassesService glassesService) {
        this.glassesService = glassesService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        String[] path = request.getPathInfo().split("/");
        long id = Long.parseLong(path[path.length-1]);
        Glasses glasses = glassesService.findById(id);
        pageVariables.put("glasses", glasses);
        response.setContentType("text/html;charset=utf-8");

        PageGenerator.instance().process("glassesCard", pageVariables, response.getWriter());
    }
}


