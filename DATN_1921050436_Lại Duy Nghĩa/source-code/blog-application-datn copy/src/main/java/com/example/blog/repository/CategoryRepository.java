package com.example.blog.repository;

import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByIdIn(List<Integer> ids);

    Optional<Category> findByName(String name);

    @Query(
            value = "select c from Category c"
    )
    Page<CategoryPublic> findCategories(Pageable pageable);

    @Query("SELECT c FROM Category c JOIN FETCH c.blogs b WHERE b.status = true ORDER BY b.publishedAt DESC")
    List<Category> findAllCategoriesWithLatestBlogs();

    @Query("select c from Category c where c.status = true")
    List<Category> findAllCategoryPublic();

    @Query("select c from Category c where upper(c.name) like upper(?1)")
    Optional<Category> findByNameLikeIgnoreCase(String name);


}