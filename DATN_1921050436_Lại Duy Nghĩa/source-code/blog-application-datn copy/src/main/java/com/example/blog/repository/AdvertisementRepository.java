package com.example.blog.repository;

import com.example.blog.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    List<Advertisement> findAllByStatus(Boolean status);

    @Query("select a from Advertisement a where a.displayOrder = ?1")
    List<Advertisement> findAllByDisplayOrder(Integer order);

    @Query("select a from Advertisement a where a.status = true and a.id != ?1 " )
    List<Advertisement> findAllByDisplayOrderNotExistCurrentAdvertisement(Integer advertisementId);
}
