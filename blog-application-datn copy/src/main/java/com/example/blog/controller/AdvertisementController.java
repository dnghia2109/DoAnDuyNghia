package com.example.blog.controller;

import com.example.blog.entity.Advertisement;
import com.example.blog.request.AdvertisementRequest;
import com.example.blog.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/dashboard/admin/advertisement")
    public String getIndex(@RequestParam(required = false, defaultValue = "1") Integer page,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                           Model model) {
        Page<Advertisement> pageInfo = advertisementService.getAllForAdmin(page, pageSize);
        model.addAttribute("page", pageInfo);
        model.addAttribute("currentPage", page);
        return "admin/advertisement/index";
    }

    @GetMapping("/dashboard/admin/advertisement/create")
    public String getCreatePage(Model model) {
        return "admin/advertisement/create-advertisement";
    }

    @GetMapping("/dashboard/admin/advertisement/{id}/detail")
    public String getUpdatePage(@PathVariable Integer id, Model model) {
        Advertisement advertisement = advertisementService.getDetail(id);
        model.addAttribute("advertisement", advertisement);
        return "admin/advertisement/detail-advertisement";
    }

    @PostMapping("/api/v1/admin/advertisement")
    public ResponseEntity<?> createNewAdvertisement(@RequestBody AdvertisementRequest advertisementRequest) {
        return new ResponseEntity<>(advertisementService.create(advertisementRequest), HttpStatus.OK);
    }

    @PutMapping("/api/v1/admin/advertisement/{id}")
    public ResponseEntity<?> updateAdvertisement(@PathVariable Integer id, @RequestBody AdvertisementRequest advertisementRequest) {
        return new ResponseEntity<>(advertisementService.update(id, advertisementRequest), HttpStatus.OK);
    }

    @GetMapping("/api/v1/advertisement/get-all")
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        return new ResponseEntity<>(advertisementService.getAllForAdmin(page, pageSize), HttpStatus.OK);
    }

//    @PutMapping
}
