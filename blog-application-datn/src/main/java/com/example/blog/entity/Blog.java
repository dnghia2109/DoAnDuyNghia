package com.example.blog.entity;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.BlogDto;
import com.example.blog.dto.CommentDto;
import com.example.blog.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//@SqlResultSetMapping(
//        name = "BlogDtoPublic",
//        classes = @ConstructorResult(
//                targetClass = BlogDto.class,
//                columns = {
//                        @ColumnResult(name = "id", type = Integer.class),
//                        @ColumnResult(name = "title", type = String.class),
//                        @ColumnResult(name = "slug", type = String.class),
//                        @ColumnResult(name = "description", type = String.class),
//                        @ColumnResult(name = "content", type = String.class),
//                        @ColumnResult(name = "thumbnail", type = String.class),
//                        @ColumnResult(name = "published_at", type = LocalDateTime.class),
//                        @ColumnResult(name = "user_id"),
//                        @ColumnResult(name = "category_id"),
//                        @ColumnResult(name = "comments"),
//
//                }
//        )
//)
//
//@NamedNativeQuery(
//        name = "getAllBlogDto",
//        resultSetMapping = "BlogDtoPublic",
//        query = "SELECT b.id, b.title, b.slug, b.description, b.content, b.thumbnail," +
//                " b.published_at, b.user_id, bc.category_id, c.id AS 'comment_id' " +
//                "FROM blog b" +
//                "INNER JOIN blog_category bc ON bc.blog_id = b.id" +
//                "INNER JOIN comment c ON c.blog_id = b.id"
//)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "published_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime publishedAt;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "approve_status")
    @Enumerated(EnumType.STRING)
    private EApprovalStatus approvalStatus;

    @Column(name = "note", length = 300)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "blog_category",
//            joinColumns = @JoinColumn(name = "blog_id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id"))
//    private List<Category> categories = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "blog_tags",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private List<Tag> tags = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        note = null;
        if(status) {
            publishedAt = createdAt;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        if(status) {
            publishedAt = updatedAt;
        }
    }

    // Xóa cate thì list cate của blog cũng xóa
    public void removeCategory(Category category) {
        this.category = null;
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }
}
