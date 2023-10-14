package com.example.blog.repository;

import com.example.blog.dto.BlogDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Page<BlogPublic> findByStatusOrderByPublishedAtDesc(Pageable pageable, Boolean status);

//    Page<Blog> findByStatusOrderByPublishedAtDesc1(Pageable pageable, Boolean status);

    List<Blog> findByTitleContainsIgnoreCaseAndStatusOrderByPublishedAtDesc(String title, Boolean status);

    //Lâấy chi tiết bài viết
    //Optional<Blog> findByIdAndSlugAndStatus(Integer id, String slug, Boolean status);
    Optional<Blog> findByIdAndSlugAndStatus(Integer id, String slug, Boolean status);

    // Lấy ds blog theo category
    List<Blog> findByCategories_NameAndStatusOrderByPublishedAtDesc(String name, Boolean status);

    @Query(
            value = "select b from Blog b"
    )
    Page<BlogPublic> findBlogs(Pageable pageable);

    Page<BlogPublic> findByUser_IdOrderByCreatedAtDesc(Integer id, Pageable pageable);

    List<BlogPublic> findByUser_IdOrderByCreatedAtDesc(Integer id);

    // Lấy danh sách blog theo category (dùng ở method xóa category)
    List<Blog> findByCategories_IdOrderByIdAsc(Integer id);

    // Lấy danh sách blog theo tag (dùng ở method xóa tag)
    List<Blog> findByTags_IdOrderByIdAsc(Integer id);

//    @Query("SELECT b FROM Blog b " +
//            "WHERE b.categories = :category " +
//            "AND b.status = true " +
//            "ORDER BY b.publishedAt DESC")
//    List<Blog> findLatestBlogsByCategory(@Param("category") Category category, Pageable pageable);




//    @Query(value = "select new com.example.blog.dto.BlogDto(b) from Blog b where b.status = true and b.categories.")
//    List<BlogDto> findByCategoryAndStatusIsTrue(Integer id);

    List<Blog> findByStatusTrueAndCategories_Id(Integer id);

//    Page<BlogDto> findByUser_IdOrderByCreatedAtDesc(Integer id, Pageable pageable);

//    List<Blog> findByCategoryId(Integer id);



    // ============================================================================================================
    /*
     * @author: Lai Duy Nghia
     * @since: 10/10/2023 21:37
     * @description:  test wwith blog dto
     * @update:
     *
     * */
    // teest wwith blog dto
    @Query(value = "select new com.example.blog.dto.BlogDto(b) from Blog b")
    Page<BlogDto> findBlogsDto(Pageable pageable);

    @Query(value = "select new com.example.blog.dto.BlogDto(b) from Blog b")
    List<BlogDto> getAllBlogDtos();

}