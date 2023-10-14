package com.example.blog.service;

import com.example.blog.entity.Blog;
import com.example.blog.entity.Tag;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.TagRepository;
import com.example.blog.request.TagRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    public Tag createTag(TagRequest request) {
        if (request.getName() == null || request.getName().equals("")) {
            throw new BadRequestException("Name is required");
        }

        if (tagRepository.findByName(request.getName()).isPresent()) {
            throw new BadRequestException("Tag is exist");
        }

        Tag tag = new Tag();
        tag.setName(request.getName());
        tag.setStatus(request.getStatus());
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag updateTag(Integer id, TagRequest request) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found tag with id = " + id);
        });

        if (request.getName() == null || request.getName().equals("")) {
            throw new BadRequestException("Name is required");
        }

        if (tagRepository.findByName(request.getName()).isPresent()) {
            throw new BadRequestException("Tag is exist");
        }

        tag.setName(request.getName());
        tag.setStatus(request.getStatus());
        tagRepository.save(tag);

        return tag;
    }

    public void deleteTag(Integer id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found tag with id = " + id);
        });

        List<Blog> blogs = blogRepository.findByTags_IdOrderByIdAsc(id);
        // blogs.forEach(blog -> blog.removeTag(tag));
        for (Blog blog: blogs) {
            blog.removeTag(tag);
            blogRepository.save(blog);
        }
    }

    // Hiển thị danh sách các tag trong hệ thống (dùng cho trang danh sách tag admin)
    public Page<Tag> getAllTagForAdmin(Integer page, Integer pageSize) {
        return tagRepository.findAll(PageRequest.of(page - 1, pageSize, Sort.by("id").ascending()));
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }
}
