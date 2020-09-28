package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.User;
import com.rk.dto.CatalogAndMessage;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CatalogGlassesServlet extends HttpServlet {
    private GlassesService glassesService;

    public CatalogGlassesServlet() {
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
                        String nameOfCatalog = request.getParameter("searchName");
                        CatalogAndMessage catalogAndMessage = glassesService.getCatalogAndMessage(nameOfCatalog);
                        pageVariables.put("catalogList", catalogAndMessage.getCatalog());
                        pageVariables.put("information", catalogAndMessage.getMessage());
                        response.setContentType("text/html;charset=utf-8");

                        PageGenerator.instance().process("catalog", pageVariables, response.getWriter());
                        return;
                    }
                }
            }
            response.sendRedirect("/login");
        }
    }

}
