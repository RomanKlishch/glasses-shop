package com.rk.web.servlet.user;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.dto.FeaturesAndSpecialGlasses;
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

public class HomeServlet extends HttpServlet {
    private GlassesService glassesService;

    public HomeServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = new HashMap<>();
        FeaturesAndSpecialGlasses glasses = glassesService.getListsFeaturesAndSpecialGlasses(9, 6);

        List<Glasses> feature = glasses.getFeature();
        List<Glasses> special = glasses.getSpecial();

        pageVariables.put("feature", feature);
        pageVariables.put("special", special);
        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("index", pageVariables, response.getWriter());
    }
}
