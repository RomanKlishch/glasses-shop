package com.rk.web.servlet.admin;

import com.rk.service.GlassesService;

import javax.servlet.http.HttpServlet;

public class AddGlassesServlet extends HttpServlet {
    private GlassesService glassesService;
/*
    public AddGlassesServlet() {
        this.glassesService = ServiceLocator.getBean("GlassesService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        PageGenerator.instance().process("admin/addGlasses", response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Photo> photoList = new ArrayList<>();
        String[] urlPhoto = request.getParameterValues("photo");
        if (urlPhoto != null) {
            for (String address : urlPhoto) {
                if (!address.isEmpty()) {
                    Photo photo = new Photo();
                    photo.setPathToImage(address);
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

        response.sendRedirect("/glasses/"
                .concat(glasses.getCategory())
                .concat("/")
                .concat(String.valueOf(glasses.getId())));
    }*/
}
