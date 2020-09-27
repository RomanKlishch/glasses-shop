package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.User;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ArticleServlet extends HttpServlet {
    private Map<String, User> cookieTokens;

    public ArticleServlet() {
        this.cookieTokens = ServiceLocator.getBean(Map.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (cookieTokens.containsKey(cookie.getValue())) {
                        response.setContentType("text/html;charset=utf-8");
                        PageGenerator.instance().process("articles", response.getWriter());
                        return;
                    }
                }
            }
            response.sendRedirect("/login");
        }
    }
}

