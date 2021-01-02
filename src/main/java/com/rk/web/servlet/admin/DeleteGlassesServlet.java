package com.rk.web.servlet.admin;

import com.rk.service.GlassesService;

import javax.servlet.http.HttpServlet;

public class DeleteGlassesServlet extends HttpServlet {
    private GlassesService glassesService;
/*
    public DeleteGlassesServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("id"));
        glassesService.deleteById(id);

        response.sendRedirect("/home");
    }*/
}
