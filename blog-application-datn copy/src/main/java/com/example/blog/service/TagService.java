package com.example.blog.service;

import com.example.blog.dto.TagDto;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    public TagDto createTag(TagRequest request) {
        if (request.getName() == null || request.getName().equals("")) {
            throw new BadRequestException("Name is required");
        }

        if (tagRepository.findByName(request.getName()).isPresent()) {
            throw new BadRequestException("Tag is exist");
        }

        Tag tag = new Tag();
        tag.setName(request.getName());
        tag.setStatus(request.getStatus());
        tagRepository.save(tag);

        return new TagDto(tag);
    }

    @Transactional
    public TagDto updateTag(Integer id, TagRequest request) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found tag with id = " + id);
        });

        if (request.getName() == null || request.getName().equals("")) {
            throw new BadRequestException("Name is required");
        }

        if (!tag.getName().equalsIgnoreCase(request.getName()) && (tagRepository.findByName(request.getName()).isPresent())) {
            throw new BadRequestException("Tag is exist");
        }

        tag.setName(request.getName());
        tag.setStatus(request.getStatus());
        tagRepository.save(tag);

        return new TagDto(tag);
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
    public Page<TagDto> getAllTagForAdmin(Integer page, Integer pageSize) {
        return tagRepository.findAll(PageRequest.of(page - 1, pageSize, Sort.by("id").ascending())).map(TagDto::new);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll().stream().filter(Tag::getStatus).collect(Collectors.toList());
    }


}
