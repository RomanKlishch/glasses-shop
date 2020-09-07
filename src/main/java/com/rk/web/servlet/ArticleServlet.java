package com.rk.web.servlet;

import com.rk.web.templator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");

        PageGenerator.instance().process("articles", pageVariables, response.getWriter());
    }
}
