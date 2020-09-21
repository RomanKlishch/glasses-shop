package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.service.GlassesService;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddGlassesServlet extends HttpServlet {
    private GlassesService glassesService = ServiceLocator.getBean(GlassesService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PageGenerator.instance().process("admin/addGlasses", response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Glasses glasses = Glasses.builder()
                .name(request.getParameter("name"))
                .collection(request.getParameter("collection"))
                .category(request.getParameter("category"))
                .details(request.getParameter("details"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();
        glassesService.save(glasses, request.getParameterValues("photo"));
        response.sendRedirect("");
    }

}
