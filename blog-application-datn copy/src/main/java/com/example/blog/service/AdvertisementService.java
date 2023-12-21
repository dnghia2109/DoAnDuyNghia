package com.example.blog.service;

import com.example.blog.entity.Advertisement;
import com.example.blog.exception.BadRequestException;
import com.example.blog.repository.AdvertisementRepository;
import com.example.blog.request.AdvertisementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Page<Advertisement> getAllForAdmin(Integer page, Integer pageSize) {
        return advertisementRepository.findAll(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
    }

    public Advertisement create(AdvertisementRequest request) {
        if (request.getName().isEmpty()) {
            throw new BadRequestException("Tên quảng cáo bị trống!");
        }
        List<Advertisement> advertisementsActive = advertisementRepository.findAllByStatus(true);
        Advertisement advertisement = new Advertisement();
        advertisement.setName(request.getName());
        advertisement.setStatus(request.getStatus());
        advertisement.setLinkRedirect(request.getLinkRedirect() != null ? request.getLinkRedirect() : null);
        if (!request.getUrl().isEmpty()) {
            advertisement.setUrl(request.getUrl());
        } else {
            advertisement.setUrl(null);
            advertisement.setStatus(false);
        }

        advertisement.setDisplayOrder(request.getPosition());

        advertisementRepository.save(advertisement);
        return advertisement;
    }

    public Advertisement update(Integer id, AdvertisementRequest request) {
        Optional<Advertisement> optionalAdvertisement = advertisementRepository.findById(id);
        if (optionalAdvertisement.isEmpty()) {
            throw new BadRequestException("Không tim thấy quảng cáo có id - " + id);
        }
        Advertisement advertisement = optionalAdvertisement.get();
        advertisement.setName(request.getName());
        advertisement.setStatus(request.getStatus());
        advertisement.setLinkRedirect(request.getLinkRedirect() != null ? request.getLinkRedirect() : null);
        if (!request.getUrl().isEmpty()) {
            advertisement.setUrl(request.getUrl());
        } else {
            advertisement.setUrl(null);
        }

        advertisement.setDisplayOrder(request.getPosition());
        advertisementRepository.save(advertisement);
        return advertisement;
    }

    public void delete(Integer id) {
        Optional<Advertisement> optionalAdvertisement = advertisementRepository.findById(id);
        if (optionalAdvertisement.isEmpty()) {
            throw new BadRequestException("Không tim thấy quảng cáo có id - " + id);
        }
        Advertisement advertisement = optionalAdvertisement.get();
        advertisementRepository.delete(advertisement);

    }

    public List<Advertisement> getListAdvertisementByDisplayOrder(Integer order) {
        return advertisementRepository.findAllByDisplayOrder(order);
    }

    public Advertisement getDetail(Integer id) {
        Optional<Advertisement> optionalAdvertisement = advertisementRepository.findById(id);
        if (optionalAdvertisement.isEmpty()) {
            throw new BadRequestException("Không tim thấy quảng cáo có id - " + id);
        }
        return optionalAdvertisement.get();
    }

    public List<Advertisement> getAdvertisementByDisplayOrder(Integer displayOrder) {
        return advertisementRepository.findAllByDisplayOrder(displayOrder);
    }
}
