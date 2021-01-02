package com.rk.web.servlet.admin;

import com.rk.service.GlassesService;

import javax.servlet.http.HttpServlet;

public class EditGlassesServlet extends HttpServlet {
    private GlassesService glassesService;
/*
    public EditGlassesServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = new HashMap<>();
        long id = Long.parseLong(request.getParameter("id"));
        Glasses glasses = glassesService.findById(id);

        pageVariables.put("glasses", glasses);
        pageVariables.put("photos", glasses.getPhotos());

        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("admin/editGlasses", pageVariables, response.getWriter());
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        List<Photo> photoList = new ArrayList<>();
        String[] photoIds = request.getParameterValues("photoId");
        String[] urlPhotos = request.getParameterValues("photo");
        if (photoIds != null && urlPhotos != null) {
            for (int i = 0; i < photoIds.length; i++) {
                if (!photoIds[i].isEmpty() && !urlPhotos[i].isEmpty()) {
                    photoList.add(Photo.builder()
                            .id(Long.parseLong(photoIds[i])).pathToImage(urlPhotos[i]).build());
                }
            }
        }

        Glasses glasses = Glasses.builder()
                .id(Long.parseLong(request.getParameter("glassesId")))
                .name(request.getParameter("name"))
                .collection(request.getParameter("collection"))
                .category(request.getParameter("category"))
                .details(request.getParameter("details"))
                .price(Double.parseDouble(request.getParameter("price")))
                .photos(photoList)
                .build();

        glassesService.update(glasses);
        response.sendRedirect("/glasses/"
                .concat(glasses.getCategory())
                .concat("/")
                .concat(String.valueOf(glasses.getId())));
    }*/
}