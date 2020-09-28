package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.domain.User;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.ServletException;
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

    public AddGlassesServlet() {
        this.glassesService = ServiceLocator.getBean(GlassesService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    Map<String,User> cookieTokens = (Map<String, User>) request.getServletContext().getAttribute("cookieTokens");
                    User user = cookieTokens.get(cookie.getValue());
                    if (user != null && user.getRole().getUserRole().equals("ADMIN")) {
                        response.setContentType("text/html;charset=utf-8");
                        PageGenerator.instance().process("admin/addGlasses", response.getWriter());
                        return;
                    }
                }
            }
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
        response.sendRedirect("");
    }
}
