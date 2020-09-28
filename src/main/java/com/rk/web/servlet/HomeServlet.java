package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.User;
import com.rk.dto.FeaturesAndSpecialGlasses;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeServlet extends HttpServlet {
    private GlassesService glassesService;

    public HomeServlet() {
        this.glassesService = ServiceLocator.getBean(GlassesService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    Map<String, User> cookieTokens = (Map<String, User>) request.getServletContext().getAttribute("cookieTokens");
                    if (cookieTokens.containsKey(cookie.getValue())) {
                        Map<String, Object> pageVariables = new HashMap<>();

                        FeaturesAndSpecialGlasses glasses = glassesService.getListsFeaturesAndSpecialGlasses(9, 6);
                        List<Glasses> feature = glasses.getFeature();
                        List<Glasses> special = glasses.getSpecial();
                        pageVariables.put("feature", feature);
                        pageVariables.put("special", special);
                        response.setContentType("text/html;charset=utf-8");

                        PageGenerator.instance().process("index", pageVariables, response.getWriter());
                        return;
                    }
                }
            }
            response.sendRedirect("/login");
        }
    }
}
