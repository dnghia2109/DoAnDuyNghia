package com.example.blog.repository;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.BlogDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Page<BlogPublic> findByStatusOrderByPublishedAtDesc(Pageable pageable, Boolean status);

    List<Blog> findByTitleContainsIgnoreCaseAndStatusOrderByPublishedAtDesc(String title, Boolean status);

    //Lâấy chi tiết bài viết
    Optional<Blog> findByIdAndSlugAndStatus(Integer id, String slug, Boolean status);

    // Lấy ds blog theo category
    List<Blog> findByCategory_NameAndStatusOrderByPublishedAtDesc(String name, Boolean status);

    @Query(
            value = "select b from Blog b"
    )
    Page<BlogPublic> findBlogs(Pageable pageable);

    List<BlogPublic> findByUser_IdOrderByCreatedAtDesc(Integer id);

    // Lấy danh sách blog theo category (dùng ở method xóa category)
    List<Blog> findByCategory_IdOrderByIdAsc(Integer id);

    // Lấy danh sách blog theo tag (dùng ở method xóa tag)
    List<Blog> findByTags_IdOrderByIdAsc(Integer id);

    List<Blog> findByStatusTrueAndCategory_Id(Integer id);


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

    @Query(value = "select b from Blog b where b.approvalStatus = ?1 and b.status = true")
    List<Blog> getBlogsAreApproveAndStatusIsTrue(EApprovalStatus approvalStatus);

    @Query(value = "select b from Blog b where b.approvalStatus = ?1 and b.status = true order by b.publishedAt desc")
    List<Blog> getLastestNewsOrderByPublish(EApprovalStatus approvalStatus, Pageable pageable);

    @Query(value = "select new com.example.blog.dto.BlogDto(b) from Blog b where b.approvalStatus = ?1")
    Page<BlogDto> getBlogsWithApproveStatus(Pageable pageable, EApprovalStatus approvalStatus);

    @Query(value = "select new com.example.blog.dto.BlogDto(b) from Blog b where b.user.id = ?1")
    Page<BlogDto> findByUser_IdOrderByCreatedAtDesc(Integer id, Pageable pageable);

    @Query(value = "select new com.example.blog.dto.BlogDto(b) from Blog b where b.user.id = ?1 and b.approvalStatus = ?2")
    Page<BlogDto> findByUser_IdAndApprovalStatus(Integer id, EApprovalStatus approvalStatus, Pageable pageable);

    @Query("select b from Blog b where b.category.id = ?1 and b.status = true and b.approvalStatus = 'APPROVE'")
    List<Blog> getBlogsByCategory(Integer categoryId);

    @Query("SELECT b " +
            "FROM Blog b " +
            "WHERE (:keyword IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "  AND (:startDate IS NULL OR b.publishedAt >= :startDate) " +
            "  AND (:endDate IS NULL OR b.publishedAt <= :endDate)")
    Page<BlogDto> searchBlogs(String keyword, LocalDateTime startDate, LocalDateTime endDate, PageRequest of);

    @Query("SELECT b " +
            "FROM Blog b " +
            "WHERE b.status = true AND b.approvalStatus = :approvalStatus " +
            "  AND (:keyword IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "  AND (:categoryId IS NULL OR b.category.id = :categoryId) " +
            "  AND (:startDate IS NULL OR b.publishedAt >= :startDate) " +
            "  AND (:endDate IS NULL OR b.publishedAt <= :endDate)")
    Page<Blog> searchBlogs(String keyword, Integer categoryId, LocalDateTime startDate, LocalDateTime endDate, EApprovalStatus approvalStatus, Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.status = 1 AND b.approvalStatus = :approvalStatus AND (:keyword IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<BlogDto> findAllByStatusAndApprovalStatus(EApprovalStatus approvalStatus, String keyword, Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE UPPER(b.title) LIKE UPPER(CONCAT('%', :keyword, '%'))" +
            " AND " +
            " (:time = 'all' OR " +
            " (:time = 'one-day' AND b.publishedAt >= CURRENT_TIMESTAMP - 1) OR" +
            " (:time = 'one-week' AND b.publishedAt >= CURRENT_TIMESTAMP - 7) OR" +
            " (:time = 'one-month' AND b.publishedAt >= CURRENT_TIMESTAMP - 30) OR" +
            " (:time = 'one-year' AND b.publishedAt >= CURRENT_TIMESTAMP     - 365))")
    Page<Blog> searchBlogsByFilter(@Param("keyword") String keyword, @Param("time") String time, Pageable pageable);

    @Query("select b from Blog b inner join b.tags tags where tags.id = ?1 and b.status = 1 and b.approvalStatus = 'APPROVE'")
    List<Blog> findByTags_Id(Integer id);

//    @Query("select b from Blog b where b.tags.c")
//    List<Blog> findByTag(Tag tag);
}