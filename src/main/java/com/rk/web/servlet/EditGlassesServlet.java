package com.rk.web.servlet;

import com.rk.ServiceLocator;
import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.service.GlassesService;
import com.rk.web.templator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditGlassesServlet extends HttpServlet {
    private GlassesService glassesService = ServiceLocator.getBean(GlassesService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        long id = Long.parseLong(request.getParameter("id"));
        Glasses glasses = glassesService.findById(id);

        pageVariables.put("glasses", glasses);
        pageVariables.put("photos", glasses.getPhotos());

        response.setContentType("text/html;charset=utf-8");
        PageGenerator.process("admin/editGlasses", pageVariables, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Photo> photoList = new ArrayList<>();
        String[] photoIds = request.getParameterValues("photoId");
        String[] urlPhotos = request.getParameterValues("photo");
        for (int i = 0; i < photoIds.length; i++) {
            if (!photoIds[i].isEmpty() && !urlPhotos[i].isEmpty()) {
                photoList.add(Photo.builder()
                        .id(Long.parseLong(photoIds[i])).address(urlPhotos[i]).build());
            }
        }

        Glasses glasses = Glasses.builder().id(Long.parseLong(request.getParameter("glassesId")))
                .name(request.getParameter("name"))
                .collection(request.getParameter("collection"))
                .category(request.getParameter("category"))
                .details(request.getParameter("details"))
                .price(Double.parseDouble(request.getParameter("price")))
                .photos(photoList)
                .build();

        glassesService.update(glasses);
        response.sendRedirect("");
    }
}