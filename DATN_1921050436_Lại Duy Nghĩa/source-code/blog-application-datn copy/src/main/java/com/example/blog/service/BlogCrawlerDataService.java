package com.example.blog.service;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.constant.ECreateType;
import com.example.blog.dto.BlogDto;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.User;
import com.example.blog.exception.BadRequestException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.BlogCrawlDataRequest;
import com.example.blog.security.ICurrentUser;
import com.example.blog.utils.Utils;
import com.github.slugify.Slugify;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class BlogCrawlerDataService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ICurrentUser iCurrentUser;

    public BlogCrawlerDataService(BlogRepository blogRepository, UserRepository userRepository, CategoryRepository categoryRepository, ICurrentUser iCurrentUser) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.iCurrentUser = iCurrentUser;
    }

    public List<BlogDto> crawlFromListUrl(BlogCrawlDataRequest request) {
        if (request.getUrls().isEmpty() || (request.getUrls().size() == 1 && Utils.validateUrl(request.getUrls().get(0)))) {
            throw new BadRequestException("Danh sách đường dẫn trang nguồn rỗng!");
        }
        List<BlogDto> result = new ArrayList<>();
        for (String url: request.getUrls()) {
            result.add(crawlAndSaveBlogPost(url));
        }
        return result;
    }

    // Crawl và lưu vào database
    public BlogDto crawlAndSaveBlogPost(String url) {
        try {
            if (Utils.validateUrl(url)) {
                throw new BadRequestException("URL chưa đúng định dạng!");
            }
            Document doc = Jsoup.connect(url).get();
            URI uri = new URI(url);
            String host = uri.getHost();

            // get admin user
            User user = iCurrentUser.getUser();

            // Lấy thumbnail
            String imageUrl = getImageUrlFromMetaTag(doc);

            // Lấy category
            String categoryName = "";

            // Lấy ra các thông tin cần thiết
            String title = doc.title();
            String description = doc.select("meta[name=description]").attr("content");
            Element contentElement;
            switch (host) {
                case "vietnamnet.vn":
                    contentElement = doc.selectFirst("#maincontent");
                    removeArticleTags(contentElement);
                    Element breadCrumbDetailVietnamnet = doc.selectFirst(".bread-crumb-detail.sm-show-time");
                    if (breadCrumbDetailVietnamnet != null) {
                        Element ulElement = breadCrumbDetailVietnamnet.selectFirst("ul");
                        if (ulElement != null) {
                            Elements liElements = ulElement.select("li");
                            if (liElements.size() > 1) {
                                Element secondLi = liElements.get(1);
                                categoryName = secondLi.text();
                                System.out.println("Category Name: " + categoryName);
                            }
                        }
                    }
                    break;
                case "vnexpress.net":
                    contentElement = doc.selectFirst(".fck_detail");
                    Element breadCrumbDetailVnExpress = doc.selectFirst(".breadcrumb");
                    if (breadCrumbDetailVnExpress != null) {
                        Element categoryElement = breadCrumbDetailVnExpress.selectFirst("a");
                        categoryName = categoryElement.attr("title");
                    }
                    break;
                case "tuoitre.vn":
                    contentElement = doc.selectFirst("");
                    break;
                default:
                    throw new BadRequestException("Hiện tại hệ thống chưa hỗ trợ lấy dữ liệu bài viết từ trang - " + host);

            }
            Category category = null;
            // Tìm category trong hệ thống có tồn tại như tên categoryName crawl được về không
            if (!categoryName.isEmpty()) {
                Optional<Category> categoryOptional = categoryRepository.findByNameLikeIgnoreCase(categoryName);
                if (categoryOptional.isPresent()) {
                    category = categoryOptional.get();
                }
            }


            // Loại bỏ tất cả các thuộc tính trong các thẻ HTML
            cleanHtmlAttributes(contentElement);// Duyệt qua tất cả các thẻ <img> trong contentElement
            Elements imgElements = contentElement.select("img");
            for (Element img : imgElements) {
                String dataSrc = img.attr("data-src");
                String dataSet = img.attr("data-srcset");

                if (!dataSrc.isEmpty()) {
                    img.attr("src", dataSrc);
                } else if (!dataSet.isEmpty()){
                    img.attr("src", dataSet);
                }
            }
            String cleanedContent = contentElement.html();

            // Tạo slug
            Slugify slugify = Slugify.builder()
                    .customReplacement("đ", "d")
                    .customReplacement("Đ", "D")
                    .customReplacement("ê", "e")
                    .customReplacement("Ê", "E")
                    .customReplacement("ư", "u")
                    .customReplacement("Ư", "U")
                    .customReplacement("ô", "o")
                    .customReplacement("Ô", "O")
                    .customReplacement("ơ", "o")
                    .customReplacement("Ơ", "O")
                    .build();

            // Lưu vào database
            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setCategory(category);
            blog.setSlug(slugify.slugify(title));
            blog.setContent(cleanedContent);
            blog.setDescription(description);
            blog.setThumbnail(imageUrl);
            blog.setUser(user);
            blog.setCreateType(ECreateType.CRAWL_DATA);
            blog.setSourceURL(url);
            blog.setApprovalStatus(EApprovalStatus.PENDING);
            blog.setStatus(true);
            blogRepository.save(blog);
            return new BlogDto(blog);
        } catch (Exception e) {
            log.error("Error while crawling blog post", e);
            throw new BadRequestException("Đã xảy ra lỗi khi lấy dữ liệu! Vui lòng kiểm tra lại đường dẫn!");
        }
    }
    // Loại bỏ tất cả các thuộc tính trong các thẻ HTML
    public static void cleanHtmlAttributes(Element element) {
        element.removeAttr("class");
        element.removeAttr("id");
        element.removeAttr("style");
        element.removeAttr("width");
        element.removeAttr("height");
//        element.removeAttr("data-src");
        for (Element child : element.children()) {
            cleanHtmlAttributes(child);
        }
    }

    // Lấy ra URL của ảnh thumbnail từ thẻ meta
    public String getImageUrlFromMetaTag(Document document) {
        Element metaElement = document.select("meta[property=og:image]").first();
        if (metaElement != null) {
            String imageUrl = metaElement.attr("content");
            return imageUrl;
        }
        return null;
    }

    // Không lấy các bài viết gợi ý (trang vietnamnet) - các thẻ article
    public void removeArticleTags(Element element) {
        for (Element article : element.select("article")) {
            article.remove();
        }
    }
}
