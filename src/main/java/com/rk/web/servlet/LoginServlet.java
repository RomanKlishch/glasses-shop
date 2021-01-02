package com.rk.web.servlet;

import javax.servlet.http.HttpServlet;

public class LoginServlet extends HttpServlet {
/*    private SecurityService securityService;
    private PropertyReader propertyReader;

    public LoginServlet() {
        this.securityService = ServiceLocator.getBean("SecurityService");
        this.propertyReader = ServiceLocator.getBean("PropertyReader");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", request.getAttribute("message"));
        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("login", pageVariables, response.getWriter());
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = securityService.login(login, password);
        if (user != null) {
            Cookie cookie = registrationSessionAndCookie(request, user);
            response.addCookie(cookie);
            response.sendRedirect("");
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("message", "Login or password not found");
            response.setContentType(CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PageGenerator.instance().process("login", pageVariables, response.getWriter());
        }
    }

    private Cookie registrationSessionAndCookie(HttpServletRequest request, User user) {
        int lifeTime = Integer.parseInt(propertyReader.getProperty("life.time"));
        Session session = securityService.createSession(lifeTime);
        session.setUser(user);
        Map<String, Session> sessionTokens = (Map<String, Session>) request.getServletContext().getAttribute("sessionTokens");
        sessionTokens.put(session.getToken(), session);
        Cookie cookie = new Cookie("user-token", session.getToken());
        cookie.setMaxAge(lifeTime);
        return cookie;
    }*/
}
