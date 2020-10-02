package com.rk.web.servlet.user;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.User;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rk.constants.WebConstants.CONTENT_TYPE;

public class CategoryServlet extends HttpServlet {
    private GlassesService glassesService;
    private Map<String, User> userTokens;

    public CategoryServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
        this.userTokens = ServiceLocator.getBean("UserTokens");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = new HashMap<>();
        String category = request.getServletPath().substring(1);
        List<Glasses> categoryList = glassesService.getCategoryList(category);

        pageVariables.put("categoryList", categoryList);
        pageVariables.put("category", category);

        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("category", pageVariables, response.getWriter());
    }
}
