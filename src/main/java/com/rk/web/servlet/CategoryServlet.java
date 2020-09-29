package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.User;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryServlet extends HttpServlet {
    private GlassesService glassesService;
    private Map<String, User> cookieTokens;

    public CategoryServlet() {
        this.glassesService = ServiceLocator.getBean(GlassesService.class);
        this.cookieTokens = ServiceLocator.getBean(Map.class);
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (cookieTokens.containsKey(cookie.getValue())) {
                        Map<String, Object> pageVariables = new HashMap<>();
                        String category = request.getServletPath().substring(1);
                        List<Glasses> categoryList = glassesService.getCategoryList(category);

                        pageVariables.put("categoryList", categoryList);
                        pageVariables.put("category", category);
                        response.setContentType("text/html;charset=utf-8");
                        PageGenerator.instance().process("category", pageVariables, response.getWriter());
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
