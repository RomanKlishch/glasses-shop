package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.User;
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

public class CategoryServlet extends HttpServlet {
    private GlassesService glassesService;

    public CategoryServlet() {
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
            response.sendRedirect("/login");
        }
    }
}
