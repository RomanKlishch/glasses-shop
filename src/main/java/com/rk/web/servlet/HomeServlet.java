package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.User;
import com.rk.dto.FeaturesAndSpecialGlasses;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
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

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    Map<String, User> cookieTokens = (Map<String, User>) request.getServletContext().getAttribute("cookieTokens");
                    if (cookieTokens.containsKey(cookie.getValue())) {
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
            request.setAttribute("message", "You must be logged in to access this resource");
            dispatcher.forward(request, response);
        }
    }
}
