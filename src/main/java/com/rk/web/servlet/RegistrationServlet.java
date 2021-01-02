package com.rk.web.servlet;

import com.rk.service.UserService;

import javax.servlet.http.HttpServlet;

public class RegistrationServlet extends HttpServlet {
    private UserService userService;

  /*  @Override
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
    }*/
}
