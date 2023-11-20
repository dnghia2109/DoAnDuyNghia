package com.example.blog.service;

import com.example.blog.dto.BlogDto;
import com.example.blog.dto.UserDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.CategoryWebPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.User;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.security.ICurrentUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.converters.AdditionalModelsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WebService {
    private final ICurrentUser iCurrentUser;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public User getUserDetailPage() {
        return iCurrentUser.getUser();
    }


    // Lấy ds blog public được sắp xếp theo thời gian public giảm dần (có phân trang)
    public Page<BlogPublic> getAllBlog(Integer page, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        return blogRepository.findByStatusOrderByPublishedAtDesc(pageRequest, true);
    }

    // Tìm kiếm bài viết theo từ khóa
    public List<BlogPublic> searchBlog(String term) {
        List<Blog> blogList = blogRepository.findByTitleContainsIgnoreCaseAndStatusOrderByPublishedAtDesc(term, true);

        return blogList.stream()
                .map(blog -> BlogPublic.of(blog))
                .toList();
    }

    public List<BlogDto> searchBlog1(String term) {
        List<Blog> blogList = blogRepository.findByTitleContainsIgnoreCaseAndStatusOrderByPublishedAtDesc(term, true);

        return blogList.stream().map(blog -> modelMapper.map(blog, BlogDto.class)).collect(Collectors.toList());
    }

    // Lấy ds category + số bài viết áp dụng category đó
    public List<CategoryWebPublic> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(CategoryWebPublic::of)
                .filter(categoryWebPublic -> categoryWebPublic.getUsed() > 0)
                .toList();
    }

    // Lấy ds 5 category được áp dụng nhiều nhất
    public List<CategoryWebPublic> getTop5Category() {
        List<CategoryWebPublic> categoryWebPublicList = getAllCategory();
        return categoryWebPublicList.stream()
                .sorted((c1, c2) -> c2.getUsed() - (c1.getUsed()))
                .limit(5)
                .toList();
    }

    // Lấy ds blog thuộc category
    public List<BlogPublic> getBlogsOfCategory(String categoryName) {
        List<Blog> blogList = blogRepository.findByCategory_NameAndStatusOrderByPublishedAtDesc(categoryName, true);

        return blogList.stream()
                .map(blog -> BlogPublic.of(blog))
                .toList();
    }

    // Lấy chi tiết bài viết
    public BlogPublic getBlogDetail(Integer blogId, String blogSlug) {
        Blog blog = blogRepository.findByIdAndSlugAndStatus(blogId, blogSlug, true).orElseThrow(() -> {
            throw new RuntimeException(String.format("Not found blog with id = %d and slug = %s", blogId, blogSlug));
        });
        return BlogPublic.of(blog);
    }

    // Test BlogPublic trong repo
    public Page<BlogPublic> getAllBlog1(Integer page, Integer pageSize) {
        Page<BlogPublic> pageInfo = blogRepository.findBlogs(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
        return pageInfo;
    }

    /*
    * @author: Lai Duy Nghia
    * @since: 19/11/2023 16:16
    * @description:
    * @update:
    *
    * */


}
