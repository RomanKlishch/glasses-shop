package com.rk.web.servlet.user;

import javax.servlet.http.HttpServlet;

public class HomeServlet extends HttpServlet {
/*    private GlassesService glassesService;

    public HomeServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = new HashMap<>();
        FeaturesAndSpecialGlasses glasses = glassesService.getListsFeaturesAndSpecialGlasses(9, 6);

        List<Glasses> feature = glasses.getFeature();
        List<Glasses> special = glasses.getSpecial();

        pageVariables.put("feature", feature);
        pageVariables.put("special", special);
        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("index", pageVariables, response.getWriter());
    }*/
}
