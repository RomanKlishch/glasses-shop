package com.rk.web.servlet.user;

import com.rk.service.GlassesService;

import javax.servlet.http.HttpServlet;

public class CategoryServlet extends HttpServlet {
    private GlassesService glassesService;

/*    public CategoryServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = new HashMap<>();
        String category = request.getServletPath().substring(1);
        List<Glasses> categoryList = glassesService.getCategoryList(category);

        pageVariables.put("categoryList", categoryList);
        pageVariables.put("category", category);

        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("category", pageVariables, response.getWriter());
    }*/
}
