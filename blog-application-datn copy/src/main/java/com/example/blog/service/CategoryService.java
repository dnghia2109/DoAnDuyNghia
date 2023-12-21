package com.example.blog.service;

//import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.request.UpsertCategoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;
    private final BlogService blogService;

    public Page<CategoryPublic> getAllCategory(Integer page, Integer pageSize) {
        Page<CategoryPublic> pageInfo = categoryRepository.findCategories(PageRequest.of(page - 1, pageSize));
        return pageInfo;
    }

    public List<CategoryPublic> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(category -> CategoryPublic.of(category))
                .collect(Collectors.toList());
    }

    public CategoryPublic createCategory(UpsertCategoryRequest request) {
        if (request.getName() == null || request.getName().equals("")) {
            throw new BadRequestException("Name is required");
        }

        if (categoryRepository.findByName(request.getName()).isPresent()) {
            throw new BadRequestException("Category is exist");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setStatus(request.getStatus());

        categoryRepository.save(category);
        return CategoryPublic.of(category);
    }

    public CategoryPublic updateCategory(Integer id, UpsertCategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found category with id = " + id);
        });

        if (request.getName() == null || request.getName().equals("")) {
            throw new BadRequestException("Name is required");
        }

        if (categoryRepository.findByName(request.getName()).isPresent()
                && !categoryRepository.findByName(request.getName()).get().getName().equals(category.getName())) {
            throw new BadRequestException("Category is exist");
        }

        category.setName(request.getName());
        category.setStatus(request.getStatus());

        categoryRepository.save(category);
        return CategoryPublic.of(category);
    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found category with id = " + id);
        });
        Category nullCategory = categoryRepository.findByName("NULL_CATEGORY").orElseThrow(null);
        List<Blog> blogList = blogRepository.findByCategory_IdOrderByIdAsc(id);

        //blogList.forEach(blog -> blog.removeCategory(category));
        for (Blog blog: blogList) {
            blog.removeCategory(category);
            blog.setCategory(nullCategory);
            blogRepository.save(blog);
        }
        categoryRepository.delete(category);
    }

// ============================================================================================================================================================================
    /*
    * @author: Lai Duy Nghia
    * @since: 14/10/2023 14:43
    * @description:  Using CategoryDto
    * @update:
    *
    * */
    // TODO: Danh sách các category

    // TODO: Lấy ra danh sách các category hiển thị bài viết ở trang chủ(USER)
    public List<CategoryDto> getAllCategoryPublic() {
        List<String> categoryNames = List.of("Thời sự", "Xã hội", "Thế giới", "Kinh tế", "Thể thao", "Văn hóa",
                "Pháp luật", "Giáo dục", "Xe", "Công nghệ", "Du lịch", "Kinh Doanh", "Sức khỏe");
        List<CategoryDto> categoryDtos = categoryRepository.findAllCategoryPublic().stream()
//            .filter(category -> categoryNames.contains(category.getName()))
            .map(category -> {
                CategoryDto categoryDto = new CategoryDto(category);
                categoryDto.setBlogs(blogService.getBlogsEachCate(category.getId()));
                return categoryDto;
            })
            .collect(Collectors.toList());
        return categoryDtos;
    }

    // TODO: Lấy ra danh sách các category hiển thị bài viết ở trang chủ(USER)
    public List<CategoryDto> getAllCategoryPublicOnIndexPage() {
        List<String> categoryNames = List.of("Thời sự", "Xã hội", "Thế giới", "Kinh tế", "Thể thao", "Văn hóa",
                "Pháp luật", "Giáo dục", "Xe", "Công nghệ", "Kinh Doanh");
        List<CategoryDto> categoryDtos = categoryRepository.findAllCategoryPublic().stream()
            .filter(category -> categoryNames.contains(category.getName()))
                .map(category -> {
                    CategoryDto categoryDto = new CategoryDto(category);
                    categoryDto.setBlogs(blogService.getBlogsEachCate(category.getId()));
                    return categoryDto;
                })
                .collect(Collectors.toList());
        return categoryDtos;
    }


    // TODO: Lấy danh sách các category (ADMIN)
    public Page<CategoryDto> getAllCategoryPage(Integer page, Integer pageSize) {
        Page<CategoryDto> pageInfo = categoryRepository.findAll(PageRequest.of(page - 1, pageSize,
                                     Sort.by("createdAt").ascending())).map(CategoryDto::new);
        return pageInfo;
    }


    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    public List<Category> getAllCate() {
        return categoryRepository.findAll();
    }


}
