package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.domain.User;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddGlassesServlet extends HttpServlet {
    private GlassesService glassesService;
    private Map<String, User> cookieTokens;

    public AddGlassesServlet() {
        this.glassesService = ServiceLocator.getBean(GlassesService.class);
        this.cookieTokens = ServiceLocator.getBean(Map.class);
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    User user = cookieTokens.get(cookie.getValue());
                    if (user != null && user.getRole().getNameRole().equals("ADMIN")) {
                        response.setContentType("text/html;charset=utf-8");
                        PageGenerator.instance().process("admin/addGlasses", response.getWriter());
                        return;
                    }
                }
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
            request.setAttribute("message", "You must be logged in as ADMIN to access this resource");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Photo> photoList = new ArrayList<>();
        String[] urlPhoto = request.getParameterValues("photo");
        if (urlPhoto != null) {
            for (String address : urlPhoto) {
                if (!address.isEmpty()) {
                    Photo photo = new Photo();
                    photo.setAddress(address);
                    photoList.add(photo);
                }
            }
        }
        Glasses glasses = Glasses.builder()
                .name(request.getParameter("name"))
                .collection(request.getParameter("collection"))
                .category(request.getParameter("category"))
                .details(request.getParameter("details"))
                .price(Double.parseDouble(request.getParameter("price")))
                .photos(photoList)
                .build();
        glassesService.save(glasses);
        response.sendRedirect("glasses/");
    }
}
