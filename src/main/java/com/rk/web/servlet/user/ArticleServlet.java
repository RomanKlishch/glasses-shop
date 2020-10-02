package com.rk.web.servlet.user;

import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rk.constants.WebConstants.CONTENT_TYPE;

public class ArticleServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("articles", response.getWriter());
    }
}

