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

import static com.rk.constants.WebConstants.CONTENT_TYPE;

public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    public RegistrationServlet() {
        this.userService = ServiceLocator.getBean("UserService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("registration", response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = User.builder()
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
        userService.save(user);
        response.sendRedirect("/login");
    }
}
