package com.rk.web.servlet.user;

import com.rk.service.GlassesService;

import javax.servlet.http.HttpServlet;

public class GlassesServlet extends HttpServlet {
    private GlassesService glassesService;
/*    public GlassesServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = new HashMap<>();

        Session session = (Session) request.getAttribute("session");
        String[] path = request.getPathInfo().split("/");
        long id = Long.parseLong(path[path.length - 1]);

        Glasses glasses = glassesService.findById(id);
        pageVariables.put("glasses", glasses);
        pageVariables.put("role", session.getUser().getRole().getNameOfUserRole());

        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("glassesCard", pageVariables, response.getWriter());
    }*/
}


