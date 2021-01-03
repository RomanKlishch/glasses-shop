package com.rk.web.controller;

import com.rk.domain.Glasses;
import com.rk.dto.CatalogAndMessage;
import com.rk.dto.FeaturesAndSpecialGlasses;
import com.rk.security.entity.Session;
import com.rk.service.GlassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@PropertySource("classpath:properties/application.properties")
public class GlassesController {

    @Autowired
    GlassesService glassesService;

    @Autowired
    @Qualifier("sessionTokens")
    Map<String, Session> sessionTokens;

    @GetMapping(path = "/")
    public String getRandomGlasses(Model model) {
        FeaturesAndSpecialGlasses glasses = glassesService.getListsFeaturesAndSpecialGlasses(9, 6);
        List<Glasses> feature = glasses.getFeature();
        List<Glasses> special = glasses.getSpecial();
        model.addAttribute("feature", feature);
        model.addAttribute("special", special);
        return "index";
    }

    @GetMapping(path = "glasses/{category}/{id}")
    public String getGlasses(@PathVariable long id, Model model, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        Glasses glasses = glassesService.findById(id);
        model.addAttribute("glasses", glasses);
        model.addAttribute("role", session.getUser().getRole().getNameOfUserRole());
        return "glassesCard";
    }

    @GetMapping(path = "/contacts")
    public String getContact() {
        return "contacts";
    }

    @GetMapping(path = "/articles")
    public String getArticle() {
        return "articles";
    }

    @GetMapping(path = "/{category}")
    public String getCategory(@PathVariable String category, Model model) {
        List<Glasses> categoryList = glassesService.getCategoryList(category);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("category", category);
        return "category";
    }

    @GetMapping(path = "/search")
    public String searchGlasses(@RequestParam String searchName, Model model) {
        CatalogAndMessage catalogAndMessage = glassesService.searchGlasses(searchName);
        model.addAttribute("catalogList", catalogAndMessage.getCatalog());
        model.addAttribute("information", catalogAndMessage.getMessage());
        return "catalog";
    }

    @GetMapping(path = "/catalog")
    public String catalogOfGlasses(Model model) {
        CatalogAndMessage catalogAndMessage = glassesService.getCatalog();
        model.addAttribute("catalogList", catalogAndMessage.getCatalog());
        model.addAttribute("information", catalogAndMessage.getMessage());
        return "catalog";
    }


}
