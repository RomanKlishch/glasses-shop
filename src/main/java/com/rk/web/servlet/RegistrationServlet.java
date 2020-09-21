package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.service.UserService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private UserService userService = ServiceLocator.getBean(UserService.class);

    public RegistrationServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PageGenerator.instance().process("registration", response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = User.builder()
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .role(UserRole.USER).build();
        userService.save(user);
        response.sendRedirect("/login");
    }
}
