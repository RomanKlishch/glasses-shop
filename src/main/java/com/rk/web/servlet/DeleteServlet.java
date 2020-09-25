package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.service.GlassesService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private final GlassesService glassesService = ServiceLocator.getBean(GlassesService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        glassesService.deleteById(id);
        response.sendRedirect("");
    }
}
