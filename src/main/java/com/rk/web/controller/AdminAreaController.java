package com.rk.web.controller;

import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.service.GlassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@PropertySource("classpath:properties/application.properties")
public class AdminAreaController {

    @Autowired
    GlassesService glassesService;

    @GetMapping(path = "/addGlasses")
    public String getAddGlasses() {
        return "admin/addGlasses";
    }

    @PostMapping(path = "/addGlasses")
    public String addGlasses(@RequestParam String[] photo,
                             @RequestParam String name,
                             @RequestParam String collection,
                             @RequestParam String category,
                             @RequestParam String details,
                             @RequestParam Double price) {
        List<Photo> photoList = new ArrayList<>();
        if (photo != null) {
            for (String address : photo) {
                if (!address.isEmpty()) {
                    Photo newPhoto = new Photo();
                    newPhoto.setPathToImage(address);
                    photoList.add(newPhoto);
                }
            }
        }
        Glasses glasses = Glasses.builder()
                .name(name).collection(collection).category(category)
                .details(details).price(price).photos(photoList).build();

        glassesService.save(glasses);
        return "redirect:/";
    }

    @GetMapping(path = "/edit")
    public String getEditGlasses(@RequestParam long id, Model model) {
        Glasses glasses = glassesService.findById(id);

        model.addAttribute("glasses", glasses);
        model.addAttribute("photos", glasses.getPhotos());
        return "admin/editGlasses";
    }

    @PostMapping(path = "/edit")
    public String editGlasses(@RequestParam String[] photo,
                              @RequestParam String[] photoId,
                              @RequestParam long glassesId,
                              @RequestParam String name,
                              @RequestParam String collection,
                              @RequestParam String category,
                              @RequestParam String details,
                              @RequestParam Double price) {
        List<Photo> photoList = new ArrayList<>();
        if (photoId != null && photo != null) {
            for (int i = 0; i < photoId.length; i++) {
                if (!photoId[i].isEmpty() && !photo[i].isEmpty()) {
                    photoList.add(Photo.builder()
                            .id(Long.parseLong(photoId[i])).pathToImage(photo[i]).build());
                }
            }
        }
        Glasses glasses = Glasses.builder()
                .id(glassesId).name(name).collection(collection).category(category)
                .details(details).price(price).photos(photoList).build();

        glassesService.update(glasses);

        return "redirect:/";
    }

    @GetMapping(path = "/delete")
    public String deleteGlasses(@RequestParam long id) {
        glassesService.deleteById(id);
        return "redirect:/";
    }

}
