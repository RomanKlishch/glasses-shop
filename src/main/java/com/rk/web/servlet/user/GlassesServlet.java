package com.rk.web.servlet.user;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.security.entity.Session;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.rk.constants.WebConstants.CONTENT_TYPE;

public class GlassesServlet extends HttpServlet {
    private GlassesService glassesService;

    public GlassesServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = new HashMap<>();

        Session session = (Session) request.getAttribute("session");
        String[] path = request.getPathInfo().split("/");
        long id = Long.parseLong(path[path.length - 1]);

        Glasses glasses = glassesService.findById(id);
        pageVariables.put("glasses", glasses);
        pageVariables.put("role", session.getUser().getRole().getNameOfUserRole());

        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("glassesCard", pageVariables, response.getWriter());
    }
}


